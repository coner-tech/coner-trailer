package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Club
import tech.coner.trailer.io.constraint.ClubPersistConstraints
import tech.coner.trailer.presentation.model.ClubModel

class ClubNameStringFieldAdapter : StringFieldAdapter<Club> {
    override operator fun invoke(model: Club): String {
        return model.name
    }
}

class ClubModelAdapter(
    val name: ClubNameStringFieldAdapter,
    private val clubPersistConstraints: ClubPersistConstraints
) : Adapter<Club, ClubModel> {
    override fun invoke(model: Club): ClubModel {
        return ClubModel(
            original = model,
            adapter = this,
            constraints = clubPersistConstraints
        )
    }

}