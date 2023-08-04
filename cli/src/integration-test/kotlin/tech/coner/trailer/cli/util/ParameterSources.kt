package tech.coner.trailer.cli.util

import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.presentation.di.Format
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

object ParameterSources {
    @JvmStatic
    fun provideArgumentsForEventResultsOfAllTypesAndAllFormats(): Stream<Arguments> = StandardEventResultsTypes.all
        .flatMap { type ->
            Format.values().map { format ->
                Arguments.of(type, format)
            }
        }
        .stream()
}