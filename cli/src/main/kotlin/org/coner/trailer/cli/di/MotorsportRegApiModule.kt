package org.coner.trailer.cli.di

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.MotorsportRegApiFactory
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.coner.trailer.io.service.MotorsportRegService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

private val factory = MotorsportRegApiFactory()

fun motorsportRegApiModule(
        username: String,
        password: String,
        organizationId: String
) = DI.Module("motorsportRegApi") {
    bind<AuthenticatedMotorsportRegApi>() with singleton {
        factory.authenticatedBasic(
                username = username,
                password = password,
                organizationId = organizationId
        )
    }
    bind<MotorsportRegPersonMapper>() with singleton { MotorsportRegPersonMapper() }
    bind<MotorsportRegService>() with singleton {
        MotorsportRegService(
                authenticatedApi = instance(),
                personService = instance(),
                motorsportRegPersonMapper = instance()
        )
    }
}
