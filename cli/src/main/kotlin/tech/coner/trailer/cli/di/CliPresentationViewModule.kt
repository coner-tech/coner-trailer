package tech.coner.trailer.cli.di

import org.kodein.di.*
import tech.coner.trailer.cli.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.cli.presentation.view.BaseCommandErrorView
import tech.coner.trailer.presentation.text.view.TextView

val cliPresentationViewModule = DI.Module("tech.coner.trailer.cli.presentation.view") {

    // bindings for views that deal with CLI-specific cases
    // don't bind anything from tech.coner.trailer.presentation.view.text, etc in here

    // Base Command Error
    bind<TextView<BaseCommandErrorModel>> {
        scoped(InvocationScope).singleton {
            BaseCommandErrorView(
                terminal = instance(),
                lineSeparator = System.lineSeparator()
            )
        }
    }
}