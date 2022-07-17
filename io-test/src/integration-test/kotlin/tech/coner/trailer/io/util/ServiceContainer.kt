package tech.coner.trailer.io.util

import org.kodein.di.*
import tech.coner.trailer.datasource.snoozle.ConerTrailerDatabase
import tech.coner.trailer.di.EnvironmentHolder
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.di.ioTestKodeinDi
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.service.*
import java.nio.file.Path
import kotlin.io.path.createDirectories

class ServiceContainer(
    val root: Path,
) {

    val crispyFishRoot = root.resolve("crispy-fish").createDirectories()
    val snoozleRoot = root.resolve("snoozle").createDirectories()
    val databaseConfiguration = DatabaseConfiguration(
        name = "db",
        crispyFishDatabase = crispyFishRoot,
        snoozleDatabase = snoozleRoot,
        motorsportReg = null,
        default = true
    )
    private val configuration = Configuration(
        databases = mapOf(
            databaseConfiguration.name to databaseConfiguration
        ),
        defaultDatabaseName = databaseConfiguration.name
    )
    private val environment = TestEnvironments.temporary(
        di = ioTestKodeinDi,
        root = root,
        configuration = configuration,
        databaseConfiguration = databaseConfiguration
    )

    init {
        val environmentDi = ioTestKodeinDi.direct.on(environment)
        val configurationService: ConfigurationService = environmentDi.instance()
        configurationService.init()
        configurationService.addDatabase(ConfigAddDatabaseParam(
            name = databaseConfiguration.name,
            crispyFishDatabase = databaseConfiguration.crispyFishDatabase,
            snoozleDatabase = databaseConfiguration.snoozleDatabase,
            motorsportReg = null,
            default = databaseConfiguration.default
        ))
        val database: ConerTrailerDatabase = environmentDi.instance()
        database.openAdministrativeSession().getOrThrow()
            .apply {
                initializeDatabase().getOrThrow()
                close()
            }
    }

    fun <T> withDataSession(block: (DataSessionContext) -> T): T {
        val context = DataSessionContext(ioTestKodeinDi, environment)
        return try {
            block(context)
        } finally {
            context.diContext.value.close()
        }
    }

    suspend fun <T> onDataSession(block: suspend DataSessionContext.() -> T): T {
        val context = DataSessionContext(ioTestKodeinDi, environment)
        return try {
            context.block()
        } finally {
            context.diContext.value.close()
        }
    }

    class DataSessionContext constructor(
        di: DI,
        private val environment: EnvironmentHolder
    ) : DIAware by di {
        override val diContext = diContext { environment.openDataSession() }

        val clubs: ClubService by instance()
        val crispyFishClasses: CrispyFishClassService by instance()
        val crispyFishEventMappingContexts: CrispyFishEventMappingContextService by instance()
        val events: EventService by instance()
        val motorsportRegEvents: MotorsportRegEventService by instance()
        val motorsportRegImports: MotorsportRegImportService by instance()
        val motorsportRegMembers: MotorsportRegMemberService by instance()
        val motorsportRegPeopleMaps: MotorsportRegPeopleMapService by instance()
        val participants: ParticipantService by instance()
        val persons: PersonService by instance()
        val policies: PolicyService by instance()
        val rankingSorts: RankingSortService by instance()
        val runs: RunService by instance()
        val seasonPointsCalculatorConfigurations: SeasonPointsCalculatorConfigurationService by instance()
        val seasons: SeasonService by instance()
    }
}