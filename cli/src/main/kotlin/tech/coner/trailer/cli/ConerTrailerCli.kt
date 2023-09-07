package tech.coner.trailer.cli

import org.kodein.di.*
import tech.coner.trailer.cli.command.RootCommand
import tech.coner.trailer.cli.di.*
import tech.coner.trailer.cli.di.command.commandModule
import tech.coner.trailer.cli.di.command.parameterMapperModule
import tech.coner.trailer.di.*
import tech.coner.trailer.presentation.di.presenter.presenterModule
import tech.coner.trailer.presentation.di.view.json.jsonViewModule
import tech.coner.trailer.presentation.di.view.text.textViewModule

private val di = DI {
    importAll(
        eventResultsModule,
        viewModule,
        mordantModule,
        utilityModule,
        ioModule,
        constraintModule,
        mapperModule,
        serviceModule,
        snoozleModule,
        verifierModule,
        motorsportRegApiModule,
        cliServiceModule,
        textViewModule,
        jsonViewModule,
        presenterModule,
        cliktModule,
        cliPresentationAdapterModule,
        cliPresentationViewModule,
        parameterMapperModule,
        commandModule
    )
    bindSingleton { di }
}

object ConerTrailerCli : DIAware by di {

    @JvmStatic
    fun main(args: Array<out String>) {
        val invocation = createInvocation()
        createRootCommand(invocation).main(args)
    }

    fun createInvocation() = direct.provider<Invocation>().invoke()

    fun createRootCommand(invocation: Invocation): RootCommand {
        return direct.on(invocation).instance()
    }
}