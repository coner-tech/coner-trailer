package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import tech.coner.trailer.cli.service.StubService

val mockkServiceModule = DI.Module("mockkService") {
    bind<StubService>() with singleton { mockk<StubService>() }
}