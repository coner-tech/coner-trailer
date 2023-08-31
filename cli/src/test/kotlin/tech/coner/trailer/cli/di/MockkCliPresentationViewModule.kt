package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.cli.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.presentation.text.view.TextView

val mockkCliPresentationViewModule = DI.Module("tech.coner.trailer.cli.presentation.view") {

    // bindings for views that deal with CLI-specific cases
    // don't bind anything from tech.coner.trailer.presentation.view.text, etc in here

    // Base Command Error
    bind<TextView<BaseCommandErrorModel>> {
        scoped(InvocationScope).singleton { mockk() }
    }
}