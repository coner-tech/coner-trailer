package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.trailer.SeasonEvent
import org.coner.trailer.TestPolicies
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.LegacyBuggedPaxTimeRunScoreFactory
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ScoreMapper
import org.coner.trailer.datasource.crispyfish.util.syntheticSignageKey
import org.coner.trailer.eventresults.AutocrossFinalScoreFactory
import org.coner.trailer.eventresults.GroupedRunScoreFactory
import org.coner.trailer.eventresults.RawTimeRunScoreFactory
import org.coner.trailer.eventresults.StandardPenaltyFactory
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.query.RegistrationsQuery
import tech.coner.crispyfish.query.StagingRunsQuery
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeText

@ExperimentalPathApi
class EventFixture(
    private val seasonFixture: SeasonFixture,
    private val temp: Path,
    val crispyFishGroupingMapper: CrispyFishGroupingMapper,
    val coreSeasonEvent: SeasonEvent,
    val conePenalty: Int = 2,
    val runCount: Int
) {

    val ecfPath: Path
    val rggPath: Path
    val st1Path: Path

    init {
        require(coreSeasonEvent.event.crispyFish?.eventControlFile?.endsWith(".ecf") == true)
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
        crispyFishGroupingMapper = crispyFishGroupingMapper
    )
    val standardPenaltyFactory = StandardPenaltyFactory(TestPolicies.lsccV1)
    val rawTimeRunScoreFactory = RawTimeRunScoreFactory(standardPenaltyFactory)
    val rawTimeScoreMapper = ScoreMapper(rawTimeRunScoreFactory)
    val paxTimeRunScoreFactory = LegacyBuggedPaxTimeRunScoreFactory(standardPenaltyFactory)
    val paxTimeScoreMapper = ScoreMapper(paxTimeRunScoreFactory)
    val groupedScoreMapper = ScoreMapper(GroupedRunScoreFactory(rawTimeRunScoreFactory, paxTimeRunScoreFactory))
    val autocrossFinalScoreFactory = AutocrossFinalScoreFactory()

    val rawTimeParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            scoreMapper = rawTimeScoreMapper
        ),
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
    val paxTimeParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            scoreMapper = paxTimeScoreMapper,
        ),
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
    val groupedParticipantResultMapper = ParticipantResultMapper(
        resultRunMapper = ResultRunMapper(
            cfRunMapper = CrispyFishRunMapper(),
            scoreMapper =  groupedScoreMapper
        ),
        crispyFishParticipantMapper = crispyFishParticipantMapper,
        finalScoreFactory = autocrossFinalScoreFactory
    )
}