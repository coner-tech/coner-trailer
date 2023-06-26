package tech.coner.trailer.cli.di

import de.vandermeer.asciitable.AsciiTable
import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper

val testCliktModule = DI.Module("tech.coner.trailer.cli.di") {
    bindSingleton<SeasonPointsCalculatorParameterMapper> { mockk() }
    bindSingleton { CliCoroutineScope }
}