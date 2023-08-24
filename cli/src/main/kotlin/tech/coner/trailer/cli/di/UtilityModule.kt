package tech.coner.trailer.cli.di

import com.github.ajalt.clikt.output.CliktConsole
import de.vandermeer.asciitable.AsciiTable
import org.kodein.di.*

val utilityModule = DI.Module("coner.trailer.cli.utility") {
    bindProvider<AsciiTable> {
        AsciiTable()
            .apply { renderer.lineSeparator = instance<CliktConsole>().lineSeparator }
    }
    bindSingleton<() -> AsciiTable> { provider<AsciiTable>() }
}