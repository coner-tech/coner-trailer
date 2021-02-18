package org.coner.trailer.cli.di

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.MotorsportRegApiFactory
import org.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import org.coner.trailer.io.service.MotorsportRegImportService
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.coner.trailer.io.service.MotorsportRegPeopleMapService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

private val factory = MotorsportRegApiFactory()

fun motorsportRegApiModule(
    credentials: MotorsportRegBasicCredentials
) = DI.Module("motorsportRegApi") {
    bind<AuthenticatedMotorsportRegApi>() with singleton {
        factory.authenticatedBasic(credentials = credentials)
    }
    bind<MotorsportRegPersonMapper>() with singleton { MotorsportRegPersonMapper() }
    bind<MotorsportRegMemberService>() with singleton { MotorsportRegMemberService(
            authenticatedApi = instance()
    ) }
    bind<MotorsportRegImportService>() with singleton {
        MotorsportRegImportService(
                personService = instance(),
                motorsportRegMemberService = instance(),
                motorsportRegPersonMapper = instance()
        )
    }
    bind<MotorsportRegPeopleMapService>() with singleton { MotorsportRegPeopleMapService(
        motorsportRegEventService = instance(),
        motorsportRegParticipantMapper = instance(),
        crispyFishGroupingService = instance()
    ) }
}
