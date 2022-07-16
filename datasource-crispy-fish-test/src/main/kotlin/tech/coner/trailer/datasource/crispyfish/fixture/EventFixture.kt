package tech.coner.trailer.datasource.crispyfish.fixture

import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingRun
import tech.coner.trailer.SeasonEvent
import tech.coner.trailer.eventresults.StandardPenaltyFactory
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.eventresults.LegacyBuggedPaxTimeRunScoreFactory
import tech.coner.trailer.datasource.crispyfish.eventresults.ParticipantResultMapper
import tech.coner.trailer.datasource.crispyfish.eventresults.ResultRunMapper
import tech.coner.trailer.eventresults.AutocrossFinalScoreFactory
import tech.coner.trailer.eventresults.ClazzRunScoreFactory
import tech.coner.trailer.eventresults.RawTimeRunScoreFactory
import tech.coner.trailer.eventresults.RunEligibilityQualifier
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
        val ecfName = "/${coreSeasonEvent.event.crispyFish?.eventControlFile}"
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

    fun registrations() = eventControlFile().queryRegistrations()

    fun allRuns(registrations: List<Registration>, stagingRuns: List<StagingRun>): List<Pair<Registration?, Run?>> {
        val registrationsBySignageKey: Map<tech.coner.crispyfish.model.Signage, Registration> =
            registrations.associateBy { registration -> registration.signage }
        return stagingRuns
            .map { stagingRun ->
                val registration = stagingRun.stagingRegistration?.signage?.let { registrationsBySignageKey[it] }
                registration to stagingRun.run
            }
    }

    fun stagingRuns(registrations: List<Registration>): List<StagingRun> {
        return eventControlFile().queryStagingRuns(registrations = registrations)
    }

    val crispyFishParticipantMapper = CrispyFishParticipantMapper(
        crispyFishClassingMapper = crispyFishClassingMapper
    )
    val crispyFishRunMapper = CrispyFishRunMapper()
    val standardPenaltyFactory = StandardPenaltyFactory(TestPolicies.lsccV1)
    val rawTimeRunScoreFactory = RawTimeRunScoreFactory(standardPenaltyFactory)
    val paxTimeRunScoreFactory = LegacyBuggedPaxTimeRunScoreFactory(standardPenaltyFactory)
    val clazzRunScoreFactory = ClazzRunScoreFactory(rawTimeRunScoreFactory, paxTimeRunScoreFactory)
    val autocrossFinalScoreFactory = AutocrossFinalScoreFactory()
    val runEligibilityQualifier = RunEligibilityQualifier()

    val rawTimeParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = crispyFishRunMapper,
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = rawTimeRunScoreFactory
        ),
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
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        crispyFishRunMapper = crispyFishRunMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
    val groupedParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = clazzRunScoreFactory
        ),
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        crispyFishRunMapper = crispyFishRunMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
}