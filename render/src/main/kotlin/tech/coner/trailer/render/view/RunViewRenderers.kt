package tech.coner.trailer.render.view

import tech.coner.trailer.Policy
import tech.coner.trailer.Run

interface RunsViewRenderer : ViewRenderer<RunsViewRenderer.Model> {
    data class Model(val runs: Collection<Run>, val policy: Policy)

    fun render(runs: Collection<Run>, policy: Policy) = render(Model(runs, policy))
    operator fun invoke(runs: Collection<Run>, policy: Policy) = render(runs, policy)
}