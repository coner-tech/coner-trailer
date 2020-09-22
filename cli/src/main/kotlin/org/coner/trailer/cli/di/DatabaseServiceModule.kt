package org.coner.trailer.cli.di

import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.datasource.snoozle.*
import org.coner.trailer.io.constraint.*
import org.coner.trailer.io.mapper.EventPointsCalculatorMapper
import org.coner.trailer.io.mapper.PersonMapper
import org.coner.trailer.io.mapper.RankingSortMapper
import org.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

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
}