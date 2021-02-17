package org.coner.trailer.cli.di

import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallPaxTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.ParticipantResultMapper
import org.coner.trailer.datasource.snoozle.*
import org.coner.trailer.eventresults.EventResultsReportFileNameGenerator
import org.coner.trailer.io.constraint.*
import org.coner.trailer.io.mapper.*
import org.coner.trailer.io.service.*
import org.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import org.kodein.di.*
import kotlin.io.path.ExperimentalPathApi

@OptIn(ExperimentalPathApi::class)
fun databaseServiceModule(databaseConfiguration: DatabaseConfiguration) = DI.Module("service") {
    bind<DatabaseConfiguration>() with instance(databaseConfiguration)
    bind<ConerTrailerDatabase>() with singleton { ConerTrailerDatabase(
            root = databaseConfiguration.snoozleDatabase)
    }

    // Event Points Calculators
    bind<EventPointsCalculatorResource>() with singleton {
        instance<ConerTrailerDatabase>().entity()
    }
    bind<EventPointsCalculatorMapper>() with singleton {
        EventPointsCalculatorMapper()
    }
    bind<EventPointsCalculatorPersistConstraints>() with singleton {
        EventPointsCalculatorPersistConstraints(
                resource = instance(),
                mapper = instance()
        )
    }
    bind<EventPointsCalculatorService>() with singleton {
        EventPointsCalculatorService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance()
        )
    }

    // Ranking Sorts
    bind<RankingSortResource>() with singleton {
        instance<ConerTrailerDatabase>().entity()
    }
    bind<RankingSortMapper>() with singleton {
        RankingSortMapper()
    }
    bind<RankingSortPersistConstraints>() with singleton {
        RankingSortPersistConstraints(
                resource = instance(),
                mapper = instance()
        )
    }
    bind<RankingSortService>() with singleton {
        RankingSortService(
                resource = instance(),
                mapper = instance(),
                persistConstraints = instance()
        )
    }

    // SeasonPointsCalculatorConfigurations
    bind<SeasonPointsCalculatorConfigurationResource>() with singleton {
        instance<ConerTrailerDatabase>().entity()
    }
    bind<SeasonPointsCalculatorConfigurationMapper>() with singleton {
        SeasonPointsCalculatorConfigurationMapper(
                eventPointsCalculatorService = instance(),
                rankingSortService = instance()
        )
    }
    bind<SeasonPointsCalculatorConfigurationConstraints>() with singleton {
        SeasonPointsCalculatorConfigurationConstraints(
                resource = instance(),
                mapper = instance()
        )
    }
    bind<SeasonPointsCalculatorConfigurationService>() with singleton {
        SeasonPointsCalculatorConfigurationService(
                resource = instance(),
                mapper = instance(),
                constraints = instance()
        )
    }

    // People
    bind<PersonResource>() with singleton { instance<ConerTrailerDatabase>().entity() }
    bind<PersonMapper>() with singleton { PersonMapper() }
    bind<PersonPersistConstraints>() with singleton { PersonPersistConstraints() }
    bind<PersonDeleteConstraints>() with singleton { PersonDeleteConstraints() }
    bind<PersonService>() with singleton { PersonService(
            persistConstraints = instance(),
            deleteConstraints = instance(),
            resource = instance(),
            mapper = instance()
    ) }

    // Seasons
    bind<SeasonResource>() with singleton { instance<ConerTrailerDatabase>().entity() }
    bind<SeasonMapper>() with singleton { SeasonMapper(
            seasonPointsCalculatorConfigurationService = instance()
    ) }
    bind<SeasonPersistConstraints>() with singleton { SeasonPersistConstraints(
            resource = instance(),
            mapper = instance()
    ) }
    bind<SeasonDeleteConstraints>() with singleton { SeasonDeleteConstraints() }
    bind<SeasonService>() with singleton { SeasonService(
            resource = instance(),
            mapper = instance(),
            persistConstraints = instance(),
            deleteConstraints = instance()
    ) }

    // Events
    bind<EventResource>() with singleton { instance<ConerTrailerDatabase>().entity() }
    bind<EventMapper>() with singleton { EventMapper(
        personService = instance(),
        crispyFishGroupingService = instance()
    ) }
    bind<EventPersistConstraints>() with singleton { EventPersistConstraints(
        resource = instance()
    ) }
    bind<EventDeleteConstraints>() with singleton { EventDeleteConstraints() }
    bind<EventService>() with singleton { EventService(
        resource = instance(),
        mapper = instance(),
        persistConstraints = instance(),
        deleteConstraints = instance(),
        eventCrispyFishPersonMapVerifier = instance(),
        motorsportRegEventService = instance()
    ) }
    bind<CrispyFishLoadConstraints>() with singleton { CrispyFishLoadConstraints(
        crispyFishDatabase = databaseConfiguration.crispyFishDatabase
    ) }
    bind<CrispyFishPersonMapper>() with singleton { CrispyFishPersonMapper() }
    bind<CrispyFishParticipantMapper>() with singleton { CrispyFishParticipantMapper(
        crispyFishGroupingMapper = instance()
    ) }
    bind<EventCrispyFishPersonMapVerifier>() with singleton { EventCrispyFishPersonMapVerifier(
        personService = instance(),
        crispyFishParticipantMapper = instance(),
        motorsportRegPeopleMapService = instance()
    ) }
    bind<CrispyFishEventMappingContextService>() with singleton { CrispyFishEventMappingContextService(
        crispyFishDatabase = databaseConfiguration.crispyFishDatabase,
        loadConstraints = instance()
    ) }

    // Event Results
    bind<ParticipantResultMapper>() with singleton { ParticipantResultMapper(
        crispyFishParticipantMapper = instance()
    ) }
    bind<OverallRawTimeResultsReportCreator>() with singleton { OverallRawTimeResultsReportCreator(
        participantResultMapper = instance()
    ) }
    bind<OverallPaxTimeResultsReportCreator>() with singleton { OverallPaxTimeResultsReportCreator(
        participantResultMapper = instance()
    ) }

    // Groupings
    bind<CrispyFishGroupingService>() with singleton { CrispyFishGroupingService(
        crispyFishRoot = databaseConfiguration.crispyFishDatabase.toFile(),
        mapper = instance()
    ) }
    bind<CrispyFishGroupingMapper>() with singleton { CrispyFishGroupingMapper() }


    bind<FileOutputDestinationResolver>() with singleton { FileOutputDestinationResolver(
        eventResultsReportFileNameGenerator = instance()
    ) }
    bind<EventResultsReportFileNameGenerator>() with singleton { EventResultsReportFileNameGenerator() }
}
