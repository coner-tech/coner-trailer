package tech.coner.trailer.presentation.testsupport.di.view.json

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.view.json.JsonView

val mockkJsonViewModule = DI.Module("tech.coner.trailer.presentation.view.json mockk") {
    bindSingleton<JsonView<Model>> { mockk() }
}