import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.io.service.MotorsportRegImportService

fun Assert<MotorsportRegImportService.ImportMembersAsPeopleResult>.updated() = prop("updated", MotorsportRegImportService.ImportMembersAsPeopleResult::updated)
fun Assert<MotorsportRegImportService.ImportMembersAsPeopleResult>.created() = prop("created", MotorsportRegImportService.ImportMembersAsPeopleResult::created)