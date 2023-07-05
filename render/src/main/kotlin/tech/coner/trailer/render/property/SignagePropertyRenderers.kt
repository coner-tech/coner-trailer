package tech.coner.trailer.render.property

import tech.coner.trailer.Policy
import tech.coner.trailer.Signage

fun interface SignagePropertyRenderer : PropertyRenderer<SignagePropertyRenderer.Model> {
    data class Model(val signage: Signage?, val policy: Policy)

    fun render(signage: Signage?, policy: Policy): String = render(Model(signage, policy))
    operator fun invoke(signage: Signage?, policy: Policy) = render(signage, policy)
}