package tech.coner.trailer

import java.nio.file.Path
import java.time.LocalDate
import java.util.*

data class Event(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val date: LocalDate,
    val lifecycle: Lifecycle,
    val crispyFish: CrispyFishMetadata?,
    val motorsportReg: MotorsportRegMetadata?,
    val policy: Policy,
) {

    init {
        if (policy.authoritativeRunDataSource == Policy.DataSource.CrispyFish) {
            checkNotNull(crispyFish) {
                "Event policy defines authoritative run source as crispy fish, but event lacks crispy fish metadata"
            }
        }
    }

    data class CrispyFishMetadata(
        val eventControlFile: Path,
        val classDefinitionFile: Path,
        val peopleMap: Map<PeopleMapKey, Person>
    ) {

        data class PeopleMapKey(
            val classing: Classing,
            val number: String,
            val firstName: String,
            val lastName: String
        )
    }

    data class MotorsportRegMetadata(
        val id: String
    )

    enum class Lifecycle {
        CREATE,
        PRE,
        ACTIVE,
        POST,
        FINAL
    }

    fun requireCrispyFish(): CrispyFishMetadata {
        return requireNotNull(crispyFish) { "Event lacks crispy fish metadata" }
    }
}