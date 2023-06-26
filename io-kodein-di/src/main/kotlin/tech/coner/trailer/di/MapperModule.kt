package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.io.mapper.ClubMapper
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.mapper.EventPointsCalculatorMapper
import tech.coner.trailer.io.mapper.PersonMapper
import tech.coner.trailer.io.mapper.PolicyMapper
import tech.coner.trailer.io.mapper.RankingSortMapper
import tech.coner.trailer.io.mapper.SeasonMapper
import tech.coner.trailer.io.mapper.SeasonPointsCalculatorConfigurationMapper

val mapperModule = DI.Module("coner.trailer.io.mapper") {
    bind {
        scoped(DataSessionScope).singleton { ClubMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton { EventPointsCalculatorMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton { RankingSortMapper() }
    }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonPointsCalculatorConfigurationMapper(
                eventPointsCalculatorService = instance(),
                rankingSortService = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { PersonMapper() } }
    bind {
        scoped(DataSessionScope).singleton {
            SeasonMapper(
                seasonPointsCalculatorConfigurationService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            EventMapper(
                dbConfig = context.environment.requireDatabaseConfiguration(),
                personService = instance(),
                crispyFishClassService = instance(),
                policyResource = instance(),
                policyMapper = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { CrispyFishPersonMapper() } }
    bind {
        scoped(DataSessionScope).singleton {
            CrispyFishParticipantMapper(
                crispyFishClassingMapper = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            PolicyMapper(
                clubService = instance()
            )
        }
    }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassMapper() } }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassParentMapper() } }
    bind { scoped(DataSessionScope).singleton { CrispyFishClassingMapper() } }
    bind { scoped(DataSessionScope).singleton { CrispyFishRunMapper() } }
}