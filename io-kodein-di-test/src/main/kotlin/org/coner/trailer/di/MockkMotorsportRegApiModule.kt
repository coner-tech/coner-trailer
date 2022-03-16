package org.coner.trailer.di

import io.mockk.mockk
import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.coner.trailer.io.mapper.MotorsportRegParticipantMapper
import org.coner.trailer.io.service.MotorsportRegEventService
import org.coner.trailer.io.service.MotorsportRegImportService
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.coner.trailer.io.service.MotorsportRegPeopleMapService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton

val mockkMotorsportRegApiModule = DI.Module("mockk for coner.trailer.io.motorsportRegApi") {
    bind<AuthenticatedMotorsportRegApi> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegPersonMapper> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegMemberService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegImportService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegPeopleMapService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegEventService> { scoped(DataSessionScope).singleton { mockk() } }
    bind<MotorsportRegParticipantMapper> { scoped(DataSessionScope).singleton { mockk() } }
}
