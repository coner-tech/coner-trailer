package tech.coner.trailer.io.di

import org.kodein.di.DI
import tech.coner.trailer.di.constraintModule
import tech.coner.trailer.di.eventResultsModule
import tech.coner.trailer.di.ioModule
import tech.coner.trailer.di.mapperModule
import tech.coner.trailer.di.motorsportRegApiModule
import tech.coner.trailer.di.serviceModule
import tech.coner.trailer.di.snoozleModule
import tech.coner.trailer.di.verifierModule

val ioTestKodeinDi = DI.from(listOf(
    constraintModule,
    ioModule,
    mapperModule,
    serviceModule,
    snoozleModule,
    verifierModule,
    motorsportRegApiModule,
    eventResultsModule,
))