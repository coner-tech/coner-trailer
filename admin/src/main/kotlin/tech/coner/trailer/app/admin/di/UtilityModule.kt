package tech.coner.trailer.app.admin.di

import de.vandermeer.asciitable.AsciiTable
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.provider

val utilityModule = DI.Module("coner.trailer.cli.utility") {
    bindProvider<AsciiTable> {
        AsciiTable()
            .apply { renderer.lineSeparator = System.lineSeparator() }
    }
    bindSingleton<() -> AsciiTable> { provider<AsciiTable>() }
}