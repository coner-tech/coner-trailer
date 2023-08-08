package tech.coner.trailer.datasource.crispyfish.repository

import tech.coner.crispyfish.datatype.underscorepairs.SimpleStringUnderscorePairReader
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.filetype.staging.SimpleStringStagingLineReader
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.model.*
import java.io.File

/**
 * Read the contents of a crispy fish staging log file
 *
 * The staging log file is updated instantly whenever the contents of the staging area change, such as whenever
 * a time is recorded, a penalty is assigned, etc. It's an append-only log data structure. Compared to the staging file,
 * every line in the staging log file additionally contains a timestamp when the change occurred, as well as an index
 * number of the staging file line that was changed. It will be somewhat more expensive to read this data, so it
 * should only be used when the timeliness of information is essential, such as when the event is active, to drive
 * live results, announcer displays, etc. It should definitely not be used for generating an archive of final results
 * of past events.
 */
class CrispyFishStagingLogRepository {

    /**
     * Get the latest version of every run in the staging log
     */
    fun getDistinctRuns(eventControlFile: EventControlFile, eventDay: EventDay): List<StagingRun> {
        val underscorePairReader = SimpleStringUnderscorePairReader()
        val stagingReader = SimpleStringStagingLineReader(underscorePairReader)
        val assistant = StagingFileAssistant()
        val categories = eventControlFile.queryCategories()
        val handicaps = eventControlFile.queryHandicaps()
        val registrations = eventControlFile.queryRegistrations(
            categories = categories,
            handicaps = handicaps
        )
        val registrationsBySignage = registrations.associateBy(Registration::signage)
        val stagingLogFile = eventControlFile.file
            .let { File(it.parentFile, "${it.nameWithoutExtension}.st${eventDay.sequence}_log") }
        fun toRun(stagingLogFileLine: String): Run {
            val raw = stagingReader.getRunRawTime(stagingLogFileLine)
            val pax = stagingReader.getRunPaxTime(stagingLogFileLine)
            val penalty = stagingReader.getRunPenalty(stagingLogFileLine)
            val runPenaltyType = assistant.convertStagingRunPenaltyStringToPenaltyType(penalty).getOrElse { PenaltyType.UNKNOWN }
            return Run(
                number = stagingReader.getRunNumber(stagingLogFileLine)?.toIntOrNull(),
                rawTime = assistant.convertStagingTimeStringToDuration(raw).getOrNull(),
                paxTime = assistant.convertStagingTimeStringToDuration(pax).getOrNull(),
                penaltyType = runPenaltyType,
                cones = penalty?.let {
                    if (runPenaltyType === PenaltyType.CONE) assistant.convertStagingRunPenaltyStringToConeCount(it).getOrNull()
                    else null
                },
                timeScored = null,
                timeScratchAsString = raw,
                timeScratchAsDuration = assistant.convertStagingTimeStringToDuration(raw).getOrNull()
            )
        }
        fun toClassing(
            compositeClassField: String?,
            categories: List<ClassDefinition>,
            handicaps: List<ClassDefinition>
        ): Classing {
            var handicapAbbreviation = handicaps.firstOrNull { it.abbreviation == compositeClassField }?.abbreviation
            val category = if (compositeClassField != null && handicapAbbreviation == null) {
                categories
                    .firstOrNull { compositeClassField.startsWith(it.abbreviation) }
                    ?.also { handicapAbbreviation = compositeClassField.replaceFirst(it.abbreviation, "") }
            } else {
                null
            }

            val handicap = handicaps.firstOrNull { it.abbreviation == handicapAbbreviation }

            return Classing(
                category = category,
                handicap = handicap
            )
        }
        fun toStagingLineRegistration(stagingLine: String): StagingRegistration? {
            val driverClass = stagingReader.getRegisteredDriverClass(stagingLine)?.uppercase()
            val driverNumber = stagingReader.getRegisteredDriverNumber(stagingLine)
            return if (driverClass != null || driverNumber != null) {
                StagingRegistration(
                    signage = Signage(
                        classing = toClassing(driverClass, categories, handicaps),
                        number = driverNumber
                    ),
                    classing = driverClass,
                    number = driverNumber,
                    name = stagingReader.getRegisteredDriverName(stagingLine),
                    car = stagingReader.getRegisteredDriverCar(stagingLine),
                    carColor = stagingReader.getRegisteredDriverCarColor(stagingLine)
                )
            } else {
                null
            }
        }
        fun toStagingRun(stagingLine: String): StagingRun {
            val stagingLineRegistration = toStagingLineRegistration(stagingLine)
            return StagingRun(
                stagingRegistration = stagingLineRegistration,
                registration = stagingLineRegistration?.signage?.let { registrationsBySignage[it] },
                run = toRun(stagingLine)
            )
        }
        return stagingLogFile
            .readLines()
            .groupBy { underscorePairReader.get(it, "srw")?.toIntOrNull() }
            .mapNotNull {
                when (it.key) {
                    is Int -> toStagingRun(it.value.last())
                    else -> null
                }
            }
    }
}