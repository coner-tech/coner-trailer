package tech.coner.trailer.app.admin.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.app.admin.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.presentation.library.adapter.Adapter

val mockkCliPresentationAdapterModule = DI.Module("tech.coner.trailer.app.admin.presentation.adapter mockk") {

    // Base Command Error
    bind<tech.coner.trailer.presentation.library.adapter.Adapter<Throwable, BaseCommandErrorModel>> {
        scoped(InvocationScope).singleton { mockk() }
    }
}