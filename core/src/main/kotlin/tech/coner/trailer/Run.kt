package tech.coner.trailer

data class Run(
    val sequence: Int,
    val signage: Signage? = null,
    val participant: Participant? = null,
    val cones: Int = 0,
    val didNotFinish: Boolean = false,
    val disqualified: Boolean = false,
    val rerun: Boolean = false,
    val time: Time? = null
) {
    init {
        require(cones >= 0) { "Cones must be greater than or equal to 0 but was $cones" }
    }

    val clean: Boolean
        get() = !disqualified && !didNotFinish && cones == 0

    val effectivePenalty: Penalty? by lazy {
        allPenalties?.firstOrNull()
    }

    val supersededPenalties: List<Penalty>? by lazy {
        val allPenalties = allPenalties
        if (allPenalties != null && allPenalties.size > 1) {
            allPenalties.slice(1..allPenalties.lastIndex)
        } else {
            null
        }
    }

    val allPenalties: List<Penalty>? by lazy {
        if (clean) null
        else mutableListOf<Penalty>().apply {
            if (disqualified) add(Penalty.Disqualified)
            if (didNotFinish) add(Penalty.DidNotFinish)
            if (cones > 0) add(Penalty.Cone(cones))
        }
    }

    sealed class Penalty {
        data class Cone(val count: Int) : Penalty()
        object DidNotFinish : Penalty()
        object Disqualified : Penalty()
    }
}