package tech.coner.trailer.cli.di

import io.mockk.mockk
import tech.coner.trailer.cli.service.StubService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val mockkServiceModule = DI.Module("mockkService") {
    bind<StubService>() with singleton { mockk<StubService>() }
}