package tech.coner.trailer.cli.di

import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val cliktModule = DI.Module("coner.trailer.cli.clikt") {
        bindSingleton { SeasonPointsCalculatorParameterMapper(eventPointsCalculatorService = instance()) }
}