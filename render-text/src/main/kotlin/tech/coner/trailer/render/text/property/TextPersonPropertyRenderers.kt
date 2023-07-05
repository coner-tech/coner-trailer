package tech.coner.trailer.render.text.property

import tech.coner.trailer.Person
import tech.coner.trailer.render.property.*

class TextPersonIdPropertyRenderer : PersonIdPropertyRenderer {
    override fun render(model: Person): String {
        return "${model.id}"
    }
}
class TextPersonFirstNamePropertyRenderer : PersonFirstNamePropertyRenderer {
    override fun render(model: Person): String {
        return model.firstName
    }
}
class TextPersonLastNamePropertyRenderer : PersonLastNamePropertyRenderer {
    override fun render(model: Person): String {
        return model.lastName
    }
}
class TextPersonClubMemberIdPropertyRenderer : PersonClubMemberIdPropertyRenderer {
    override fun render(model: Person): String {
        return model.clubMemberId ?: ""
    }
}
class TextPersonMotorsportRegMemberIdPropertyRenderer : PersonMotorsportRegMemberIdPropertyRenderer {
    override fun render(model: Person): String {
        return model.motorsportReg?.memberId ?: ""
    }
}