package tech.coner.trailer.toolkit.sample.dmvapp.cli.command

import com.github.ajalt.clikt.core.subcommands
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new

val cliCommandModule by DI.Module {
    bindSingleton {
        RootCommand()
            .subcommands(
                instance<DriversLicenseApplicationCommand>()
            )
    }
    bindSingleton { new(::DriversLicenseApplicationCommand) }
}