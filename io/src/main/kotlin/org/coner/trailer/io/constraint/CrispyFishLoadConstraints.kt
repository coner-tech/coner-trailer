package org.coner.trailer.io.constraint

import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.isReadable
import kotlin.io.path.isRegularFile

class CrispyFishLoadConstraints(
    private val crispyFishDatabase: Path
) : Constraint<CrispyFishEventMappingContext.Key>() {

    override fun assess(candidate: CrispyFishEventMappingContext.Key) {
        assess(candidate.classDefinitionFile, "classDefinitionFile")
        constrain(candidate.classDefinitionFile.extension == "def") {
            "$SUBJECT_CLASS_DEFINITION_FILE must be a .def file"
        }
        assess(candidate.eventControlFile, "eventControlFile")
        constrain(candidate.eventControlFile.extension == "ecf") {
            "$SUBJECT_EVENT_CONTROL_FILE must be a .ecf file"
        }
    }

    private fun assess(candidate: Path, subject: String) {
        constrain(candidate.isAbsolute) {
            "$subject must be an absolute path"
        }
        constrain(candidate.isRegularFile(LinkOption.NOFOLLOW_LINKS)) {
            "$subject must be a regular file"
        }
        constrain(candidate.isReadable()) {
            "$subject must be readable"
        }
        constrain(candidate.startsWith(crispyFishDatabase)) {
            "$subject must be within crispy fish database"
        }
    }

    companion object {
        const val SUBJECT_CLASS_DEFINITION_FILE = "classDefinitionFile"
        const val SUBJECT_EVENT_CONTROL_FILE = "eventControlFile"
    }

}