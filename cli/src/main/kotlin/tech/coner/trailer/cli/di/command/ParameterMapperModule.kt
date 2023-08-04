package tech.coner.trailer.cli.di.command

import org.kodein.di.*
import tech.coner.trailer.cli.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import tech.coner.trailer.di.DataSessionScope

val parameterMapperModule = DI.Module("tech.coner.trailer.cli.command - parameter mappers") {
    bind { scoped(DataSessionScope).singleton { SeasonPointsCalculatorParameterMapper(eventPointsCalculatorService = instance()) } }
}