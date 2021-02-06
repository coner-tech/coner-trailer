package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import org.coner.crispyfish.filetype.staging.StagingFileAssistant
import org.coner.crispyfish.query.RegistrationsQuery
import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import java.io.File
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
    val memberIdToPeople: Map<String, Person>,
    val coreSeasonEvent: SeasonEvent,
    val conePenalty: Int = 2
) {

    val ecfPath: Path
    val rggPath: Path

    init {
        require(coreSeasonEvent.event.crispyFish?.eventControlFile?.endsWith(".ecf") == true)
        val ecfName = "/seasons/${seasonFixture.path}/${coreSeasonEvent.event.crispyFish?.eventControlFile}"
        ecfPath = install(ecfName)
        rggPath = install(ecfName.replace(".ecf", ".rgg"))
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

    val participantResultMapper = ParticipantResultMapper(
        crispyFishParticipantMapper = CrispyFishParticipantMapper(
            crispyFishGroupingMapper = crispyFishGroupingMapper
        ),
        memberIdToPeople = memberIdToPeople
    )

}