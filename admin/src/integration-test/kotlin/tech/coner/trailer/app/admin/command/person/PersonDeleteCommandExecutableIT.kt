package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.exists
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import assertk.assertions.support.expected
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestPeople
import tech.coner.trailer.app.admin.command.BaseExecutableIT
import tech.coner.trailer.app.admin.util.error
import tech.coner.trailer.app.admin.util.exitCode
import tech.coner.trailer.app.admin.util.output
import kotlin.io.path.notExists

class PersonDeleteCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should delete person`() {
        val databaseName = "newly-created"
        arrange { configDatabaseAdd(databaseName) }
        arrange { configureDatabaseSnoozleInitialize() }
        val person = TestPeople.REBECCA_JACKSON
        arrange { personAdd(person) }
        val personFile = snoozleDir.resolve("people").resolve("${person.id}.json")
        assertThat(personFile, "personFile sanity check").exists()

        val processOutcome = testCommand { personDelete(person) }

        assertThat(processOutcome).all {
            exitCode().isEqualTo(0)
            output().isNullOrEmpty()
            error().isNull()
        }
        assertThat(personFile).all {
            given {
                if (it.notExists()) return@given
                else expected("to not exist but did exist")
            }
        }
    }
}