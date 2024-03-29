package tech.coner.trailer.app.admin.di

import com.github.ajalt.mordant.terminal.Terminal
import org.kodein.di.DI
import org.kodein.di.bindProvider

val mordantModule = DI.Module("coner.trailer.cli.mordant") {
    bindProvider { Terminal() }
}