package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse

class MotosportRegMemberView(
        override val console: CliktConsole
) : BaseCollectionView<GetMembersResponse.Member>() {
    override fun render(model: GetMembersResponse.Member): String {
        return """
            ${model.firstName} ${model.lastName}
                ID:         ${model.id}
                Member ID:  ${model.memberId}
        """.trimIndent()
    }
}