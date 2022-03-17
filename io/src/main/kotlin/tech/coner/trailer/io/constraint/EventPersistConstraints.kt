package tech.coner.trailer.io.constraint

import tech.coner.trailer.Event
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.io.DatabaseConfiguration
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.extension

class EventPersistConstraints(
    private val dbConfig: DatabaseConfiguration,
    private val resource: EventResource,
) : Constraint<Event>() {

    override fun assess(candidate: Event) {
        hasUniqueName(candidate.id to candidate.name).getOrThrow()
        hasEventControlFileAsRelativePath(candidate.crispyFish?.eventControlFile).getOrThrow()
        hasEventControlFileExists(candidate.crispyFish?.eventControlFile).getOrThrow()
        hasEventControlFileExtension(candidate.crispyFish?.eventControlFile).getOrThrow()
        hasClassDefinitionFileAsRelativePath(candidate.crispyFish?.classDefinitionFile).getOrThrow()
        hasClassDefinitionFileExists(candidate.crispyFish?.classDefinitionFile).getOrThrow()
        hasClassDefinitionFileExtension(candidate.crispyFish?.classDefinitionFile).getOrThrow()
    }

    val hasUniqueName = constraint<Pair<UUID, String>>(
        { (id, name) -> resource.stream { it.id != id }.noneMatch { it.name == name } },
        { "Name is not unique" }
    )

    val hasEventControlFileAsRelativePath = constraint<Path?>(
        { path -> path?.isAbsolute == false },
        { "Event Control File must be a relative path" }
    )

    val hasEventControlFileExists = constraint<Path?>(
        { path -> path
            ?.let { dbConfig.crispyFishDatabase.resolve(it) }
            ?.exists() == true
        },
        { "Event Control File must exist in Crispy Fish database" }
    )

    val hasEventControlFileExtension = constraint<Path?>(
        { path -> path?.extension == "ecf" },
        { "Event Control File must have a .ecf extension" }
    )

    val hasClassDefinitionFileAsRelativePath = constraint<Path?>(
        { path -> path?.isAbsolute == false },
        { "Class Definition File must be a relative path" }
    )

    val hasClassDefinitionFileExists = constraint<Path?>(
        { path -> path
            ?.let { dbConfig.crispyFishDatabase.resolve(it) }
            ?.exists() == true
        },
        { "Class Definition File must exist in Crispy Fish database" }
    )

    val hasClassDefinitionFileExtension = constraint<Path?>(
        { path -> path?.extension == "def" },
        { "Class Definition File must have a .def extension" }
    )
}
