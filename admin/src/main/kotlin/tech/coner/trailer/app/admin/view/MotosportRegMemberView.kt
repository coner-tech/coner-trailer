package tech.coner.trailer.app.admin.view

import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.client.motorsportreg.model.Member

class MotosportRegMemberView(
    override val terminal: Terminal
) : BaseCollectionView<Member>() {
    override fun render(model: Member): String {
        return """
            ${model.firstName} ${model.lastName}
                ID:         ${model.id}
                Member ID:  ${model.memberId}
        """.trimIndent()
    }
}