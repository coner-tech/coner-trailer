package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Club
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.presentation.model.ClubModel

class ClubNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Club> {
    override operator fun invoke(model: Club): String {
        return model.name
    }
}

class ClubModelAdapter(
    val name: ClubNameStringFieldAdapter,
    private val clubPersistConstraints: ClubPersistConstraints
) : tech.coner.trailer.presentation.library.adapter.Adapter<Club, ClubModel> {
    override fun invoke(model: Club): ClubModel {
        return ClubModel(
            item = model,
            constraints = clubPersistConstraints,
            adapter = this
        )
    }

}