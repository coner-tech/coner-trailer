package org.coner.trailer.cli.util

import org.coner.trailer.eventresults.StandardEventResultsTypes
import org.coner.trailer.render.Format
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