package tech.coner.trailer.cli.di

import com.github.ajalt.clikt.output.CliktConsole
import de.vandermeer.asciitable.AsciiTable
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val utilityModule = DI.Module("coner.trailer.cli.utility") {
    bindProvider<AsciiTable> {
        AsciiTable()
            .apply { renderer.lineSeparator = instance<CliktConsole>().lineSeparator }
    }
}