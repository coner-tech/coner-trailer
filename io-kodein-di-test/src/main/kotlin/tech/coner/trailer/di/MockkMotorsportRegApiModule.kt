package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import tech.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import tech.coner.trailer.io.mapper.MotorsportRegParticipantMapper
import tech.coner.trailer.io.service.MotorsportRegEventService
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.io.service.MotorsportRegMemberService
import tech.coner.trailer.io.service.MotorsportRegPeopleMapService

val mockkMotorsportRegApiModule = DI.Module("mockk for coner.trailer.io.motorsportRegApi") {
    bind<AuthenticatedMotorsportRegApi> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegPersonMapper> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegMemberService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegImportService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegPeopleMapService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegEventService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegParticipantMapper> { scoped(DataSessionScope).singleton { mockk() } }
}
