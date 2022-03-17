package tech.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import tech.coner.trailer.client.motorsportreg.model.Member

class MotosportRegMemberView(
        override val console: CliktConsole
) : BaseCollectionView<Member>() {
    override fun render(model: Member): String {
        return """
            ${model.firstName} ${model.lastName}
                ID:         ${model.id}
                Member ID:  ${model.memberId}
        """.trimIndent()
    }
}