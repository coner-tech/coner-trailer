package tech.coner.trailer.di

import tech.coner.trailer.client.motorsportreg.MotorsportRegApiFactory
import tech.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import tech.coner.trailer.io.mapper.MotorsportRegParticipantMapper
import tech.coner.trailer.io.service.MotorsportRegEventService
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.io.service.MotorsportRegMemberService
import tech.coner.trailer.io.service.MotorsportRegPeopleMapService
import org.kodein.di.*

val motorsportRegApiModule = DI.Module("coner.trailer.cli.motorsportRegApi") {
    bind { scoped(DataSessionScope).singleton {
        val factory = MotorsportRegApiFactory()
        factory.authenticatedBasic(credentialsSupplier = context.environment.motorsportRegCredentialSupplier)
    } }
    bind { scoped(DataSessionScope).singleton { MotorsportRegPersonMapper() } }
    bind { scoped(DataSessionScope).singleton { MotorsportRegMemberService(
            authenticatedApi = instance()
    ) } }
    bind { scoped(DataSessionScope).singleton {
        MotorsportRegImportService(
                personService = instance(),
                motorsportRegMemberService = instance(),
                motorsportRegPersonMapper = instance()
        )
    } }
    bind { scoped(DataSessionScope).singleton { MotorsportRegPeopleMapService(
        motorsportRegEventService = instance(),
        motorsportRegParticipantMapper = instance(),
        crispyFishClassService = instance()
    ) } }
    bind { scoped(DataSessionScope).singleton { MotorsportRegEventService(
        authenticatedApi = instance()
    ) } }
    bind { scoped(DataSessionScope).singleton { MotorsportRegParticipantMapper() } }
}
