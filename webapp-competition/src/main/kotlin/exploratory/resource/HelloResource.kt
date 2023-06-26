package exploratory.resource

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/hello")
class HelloResource(val to: String = "World")
