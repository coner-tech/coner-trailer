package tech.coner.trailer.io.payload

import tech.coner.trailer.EventId
import tech.coner.trailer.Policy
import java.nio.file.Path
import java.time.LocalDate

data class CreateEventPayload(
    val id: EventId? = null,
    val name: String,
    val date: LocalDate,
    val crispyFishEventControlFile: Path,
    val crispyFishClassDefinitionFile: Path,
    val motorsportRegEventId: String?,
    val policy: Policy
)