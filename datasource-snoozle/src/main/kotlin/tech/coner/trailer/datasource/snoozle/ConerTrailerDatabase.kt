package tech.coner.trailer.datasource.snoozle

import tech.coner.snoozle.db.Database
import tech.coner.snoozle.db.PathPart
import tech.coner.snoozle.db.entity.EntityResource
import tech.coner.snoozle.db.session.data.DataSession
import tech.coner.trailer.datasource.snoozle.entity.*
import java.nio.file.Path

class ConerTrailerDatabase(root: Path) : Database(root) {

    override val version = 1

    override val types = registerTypes {
        entity<ClubEntity.Key, ClubEntity> {
            path = listOf(PathPart.StringValue("club.json"))
            keyFromPath = { ClubEntity.Key }
            keyFromEntity = { ClubEntity.Key }
        }
        entity<EventPointsCalculatorEntity.Key, EventPointsCalculatorEntity> {
            path = "eventPointsCalculators" / { id } + ".json"
            keyFromPath = { EventPointsCalculatorEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { EventPointsCalculatorEntity.Key(id = id) }
        }
        entity<RankingSortEntity.Key, RankingSortEntity> {
            path = "rankingSorts" / { id } + ".json"
            keyFromPath = { RankingSortEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { RankingSortEntity.Key(id = id) }
        }
        entity<SeasonPointsCalculatorConfigurationEntity.Key, SeasonPointsCalculatorConfigurationEntity> {
            path = "seasonPointsCalculatorConfigurations" / { id } + ".json"
            keyFromPath = { SeasonPointsCalculatorConfigurationEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { SeasonPointsCalculatorConfigurationEntity.Key(id = id) }
        }
        entity<PersonEntity.Key, PersonEntity> {
            path = "people" / { id } + ".json"
            keyFromPath = { PersonEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { PersonEntity.Key(id = id) }
        }
        entity<SeasonEntity.Key, SeasonEntity> {
            path = "seasons" / { id } + ".json"
            keyFromPath = { SeasonEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { SeasonEntity.Key(id = id) }
        }
        entity<EventEntity.Key, EventEntity> {
            path = "events" / { id } + ".json"
            keyFromPath = { EventEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { EventEntity.Key(id = id) }
        }
        entity<PolicyEntity.Key, PolicyEntity> {
            path = "policies" / { id } + ".json"
            keyFromPath = { PolicyEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { PolicyEntity.Key(id = id) }
        }
    }

    override val migrations = registerMigrations {
        migrate(null to 1) {

        }
    }
}

typealias ClubResource = EntityResource<ClubEntity.Key, ClubEntity>
fun DataSession.clubs(): ClubResource = entity()

typealias EventPointsCalculatorResource = EntityResource<EventPointsCalculatorEntity.Key, EventPointsCalculatorEntity>
fun DataSession.eventPointsCalculators(): EventPointsCalculatorResource = entity()

typealias RankingSortResource = EntityResource<RankingSortEntity.Key, RankingSortEntity>
fun DataSession.rankingSorts(): RankingSortResource = entity()

typealias SeasonPointsCalculatorConfigurationResource = EntityResource<SeasonPointsCalculatorConfigurationEntity.Key, SeasonPointsCalculatorConfigurationEntity>
fun DataSession.seasonPointsCalculators(): SeasonPointsCalculatorConfigurationResource = entity()

typealias PersonResource = EntityResource<PersonEntity.Key, PersonEntity>
fun DataSession.persons(): PersonResource = entity()

typealias SeasonResource = EntityResource<SeasonEntity.Key, SeasonEntity>
fun DataSession.seasons(): SeasonResource = entity()

typealias EventResource = EntityResource<EventEntity.Key, EventEntity>
fun DataSession.events(): EventResource = entity()

typealias PolicyResource = EntityResource<PolicyEntity.Key, PolicyEntity>
fun DataSession.policies(): PolicyResource = entity()