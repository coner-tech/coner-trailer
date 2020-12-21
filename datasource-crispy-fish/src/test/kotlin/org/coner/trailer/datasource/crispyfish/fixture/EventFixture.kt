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

class EventFixture(
        val crispyFishGroupingMapper: CrispyFishGroupingMapper,
        val memberIdToPeople: Map<String, Person>,
        val coreSeasonEvent: SeasonEvent,
        val conePenalty: Int = 2
) {
    init {
        require(coreSeasonEvent.event.crispyFish?.eventControlFile?.endsWith(".ecf") == true)
    }

    fun eventControlFile(seasonFixture: SeasonFixture) = EventControlFile(
            file = File(javaClass.getResource("/seasons/${seasonFixture.path}/${coreSeasonEvent.event.crispyFish?.eventControlFile}").toURI()),
            classDefinitionFile = seasonFixture.classDefinitionFile,
            conePenalty = conePenalty,
            isTwoDayEvent = false,
            ecfAssistant = EventControlFileAssistant(),
            stagingFileAssistant = StagingFileAssistant()
    )

    fun registrations(seasonFixture: SeasonFixture) = RegistrationsQuery(
            eventControlFile = eventControlFile(seasonFixture),
            categories = seasonFixture.categories,
            handicaps = seasonFixture.handicaps
    ).query()

    val participantResultMapper = ParticipantResultMapper(
            participantMapper = CrispyFishParticipantMapper(
                    crispyFishGroupingMapper = crispyFishGroupingMapper
            ),
            memberIdToPeople = memberIdToPeople
    )

}