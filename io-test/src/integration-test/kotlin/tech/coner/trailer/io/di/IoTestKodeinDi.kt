package tech.coner.trailer.io.di

import org.kodein.di.DI
import tech.coner.trailer.di.databaseModule
import tech.coner.trailer.di.eventResultsModule
import tech.coner.trailer.di.ioModule
import tech.coner.trailer.di.motorsportRegApiModule

val ioTestKodeinDi = DI.from(listOf(
    ioModule,
    databaseModule,
    motorsportRegApiModule,
    eventResultsModule,
))