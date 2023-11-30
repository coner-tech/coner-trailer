package tech.coner.trailer.app.admin.di.command

import org.kodein.di.*
import tech.coner.trailer.app.admin.command.seasonpointscalculator.SeasonPointsCalculatorParameterMapper
import tech.coner.trailer.di.DataSessionScope

val parameterMapperModule = DI.Module("tech.coner.trailer.cli.command - parameter mappers") {
    bind { scoped(DataSessionScope).singleton { SeasonPointsCalculatorParameterMapper(eventPointsCalculatorService = instance()) } }
}