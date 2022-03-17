package tech.coner.trailer.datasource.crispyfish.fixture

import tech.coner.trailer.SeasonEvent
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.datasource.crispyfish.eventresults.LegacyBuggedPaxTimeRunScoreFactory
import tech.coner.trailer.datasource.crispyfish.eventresults.ParticipantResultMapper
import tech.coner.trailer.datasource.crispyfish.eventresults.ResultRunMapper
import tech.coner.trailer.datasource.crispyfish.util.syntheticSignageKey
import tech.coner.trailer.eventresults.*
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.query.RegistrationsQuery
import tech.coner.crispyfish.query.StagingRunsQuery
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.extension
import kotlin.io.path.writeText

class EventFixture(
    private val seasonFixture: SeasonFixture,
    private val temp: Path,
    val crispyFishClassingMapper: CrispyFishClassingMapper,
    val coreSeasonEvent: SeasonEvent,
    val conePenalty: Int = 2,
    val runCount: Int
) {

    val ecfPath: Path
    val rggPath: Path
    val st1Path: Path

    init {
        require(coreSeasonEvent.event.crispyFish?.eventControlFile?.extension == "ecf")
        val ecfName = "/seasons/${seasonFixture.path}/${coreSeasonEvent.event.crispyFish?.eventControlFile}"
        ecfPath = install(ecfName)
        rggPath = install(ecfName.replace(".ecf", ".rgg"))
        st1Path = install(ecfName.replace(".ecf", ".st1"))
    }

    fun eventControlFile(): EventControlFile {
        return EventControlFile(
            file = ecfPath.toFile(),
            classDefinitionFile = seasonFixture.classDefinitionFile,
            conePenalty = conePenalty,
            isTwoDayEvent = false,
            ecfAssistant = EventControlFileAssistant(),
            stagingFileAssistant = StagingFileAssistant()
        )
    }

    private fun install(name: String) = javaClass.getResourceAsStream(name).use {
        val text = it.bufferedReader().readText()
        val ecfPath = temp.resolve(name.substring(1))
        ecfPath.parent.createDirectories()
        ecfPath.createFile()
        ecfPath.writeText(text)
        ecfPath
    }

    fun registrations() = RegistrationsQuery(
        eventControlFile = eventControlFile(),
        categories = seasonFixture.categories,
        handicaps = seasonFixture.handicaps
    ).query()

    fun runs(registrations: List<Registration>): List<Pair<Registration?, Run?>> {
        val stagingFile = eventControlFile().stagingFile()
        val stagingRunsQuery = StagingRunsQuery(stagingFile = stagingFile)
        val stagingRuns = stagingRunsQuery.query()
        val registrationsBySignageKey: Map<String, Registration> = registrations
            .mapNotNull { registration -> registration.syntheticSignageKey()?.let { key -> key to registration } }
            .toMap()
        return stagingRuns
            .map { (stagingLineRegistration, run) ->
                val signageKey = stagingLineRegistration?.syntheticSignageKey()
                val registration = registrationsBySignageKey[signageKey]
                registration to run
            }
    }

    val crispyFishParticipantMapper = CrispyFishParticipantMapper(
        crispyFishClassingMapper = crispyFishClassingMapper
    )
    val crispyFishRunMapper = CrispyFishRunMapper()
    val standardPenaltyFactory = StandardPenaltyFactory(TestPolicies.lsccV1)
    val rawTimeRunScoreFactory = RawTimeRunScoreFactory(standardPenaltyFactory)
    val paxTimeRunScoreFactory = LegacyBuggedPaxTimeRunScoreFactory(standardPenaltyFactory)
    val groupedRunScoreFactory = GroupedRunScoreFactory(rawTimeRunScoreFactory, paxTimeRunScoreFactory)
    val autocrossFinalScoreFactory = AutocrossFinalScoreFactory()
    val runEligibilityQualifier = RunEligibilityQualifier()

    val rawTimeParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = crispyFishRunMapper,
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = rawTimeRunScoreFactory
        ),
        crispyFishClassingMapper = crispyFishClassingMapper,
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        crispyFishRunMapper = crispyFishRunMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
    val paxTimeParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = paxTimeRunScoreFactory
        ),
        crispyFishClassingMapper = crispyFishClassingMapper,
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        crispyFishRunMapper = crispyFishRunMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
    val groupedParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = groupedRunScoreFactory
        ),
        crispyFishClassingMapper = crispyFishClassingMapper,
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        crispyFishRunMapper = crispyFishRunMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
}