package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.cli.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.presentation.adapter.Adapter

val mockkCliPresentationAdapterModule = DI.Module("tech.coner.trailer.cli.presentation.adapter mockk") {

    // Base Command Error
    bind<Adapter<Throwable, BaseCommandErrorModel>> {
        scoped(InvocationScope).singleton { mockk() }
    }
}