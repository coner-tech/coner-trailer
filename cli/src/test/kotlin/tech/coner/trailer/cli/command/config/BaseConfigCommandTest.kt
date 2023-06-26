package tech.coner.trailer.cli.command.config

import org.kodein.di.diContext
import tech.coner.trailer.cli.command.AbstractCommandTest
import tech.coner.trailer.cli.command.BaseCommand

abstract class BaseConfigCommandTest<C : BaseCommand> : AbstractCommandTest<C>() {

    override val diContext = diContext { global.requireEnvironment() }
    override val setupGlobal = setupGlobalWithTempEnvironment
}