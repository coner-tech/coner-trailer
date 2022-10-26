package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import tech.coner.trailer.io.verifier.RunWithInvalidSignageVerifier

val verifierModule = DI.Module("tech.coner.trailer.io.verifier") {
    bind {
        scoped(DataSessionScope).singleton {
            EventCrispyFishPersonMapVerifier(
                personService = instance(),
                crispyFishClassService = instance(),
                crispyFishClassingMapper = instance(),
                motorsportRegPeopleMapService = instance()
            )
        }
    }
    bind {
        scoped(DataSessionScope).singleton {
            RunWithInvalidSignageVerifier()
        }
    }
}