package tech.coner.trailer.app.admin.di.command

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.di.DataSessionScope

val mockkParameterMapperModule = DI.Module("tech.coner.trailer.cli.command parameter mappers - mockk") {
    bind { scoped(DataSessionScope).singleton { mockk() } }
}