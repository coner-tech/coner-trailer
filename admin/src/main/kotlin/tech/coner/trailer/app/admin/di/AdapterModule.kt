package tech.coner.trailer.app.admin.di

import org.kodein.di.*
import tech.coner.trailer.app.admin.presentation.adapter.BaseCommandErrorAdapter
import tech.coner.trailer.app.admin.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.presentation.adapter.Adapter

val cliPresentationAdapterModule = DI.Module("tech.coner.trailer.cli.presentation.adapter") {
    bind<Adapter<Throwable, BaseCommandErrorModel>> {
        scoped(InvocationScope).singleton {
            new(::BaseCommandErrorAdapter)
        }
    }
}