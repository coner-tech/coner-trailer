package exploratory

import exploratory.service.HelloService
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val exploratoryModule = DI.Module("exploratory") {
    bindSingleton { HelloService() }
}
