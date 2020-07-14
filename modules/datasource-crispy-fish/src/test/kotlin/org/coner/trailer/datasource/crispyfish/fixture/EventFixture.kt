package org.coner.trailer.datasource.crispyfish.fixture

import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import org.coner.crispyfish.filetype.staging.StagingFileAssistant
import org.coner.crispyfish.query.RegistrationsQuery
import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent
import org.coner.trailer.datasource.crispyfish.GroupingMapper
import org.coner.trailer.datasource.crispyfish.ParticipantMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import java.io.File

class EventFixture(
        val groupingMapper: GroupingMapper,
        val memberIdToPeople: Map<String, Person>,
        val coreSeasonEvent: SeasonEvent,
        val ecfFileName: String,
        val conePenalty: Int = 2
) {
    init {
        require(ecfFileName.endsWith(".ecf"))
    }

    fun eventControlFile(seasonFixture: SeasonFixture) = EventControlFile(
            file = File(javaClass.getResource("/seasons/${seasonFixture.path}/$ecfFileName").toURI()),
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
            participantMapper = ParticipantMapper(
                    groupingMapper = groupingMapper
            ),
            memberIdToPeople = memberIdToPeople
    )

}