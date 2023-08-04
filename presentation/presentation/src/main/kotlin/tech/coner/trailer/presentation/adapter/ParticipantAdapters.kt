package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.presentation.model.ParticipantModel
import tech.coner.trailer.presentation.model.ParticipantCollectionModel

class ParticipantFirstNameStringFieldAdapter : StringFieldAdapter<Participant> {
    override fun invoke(model: Participant): String {
        return model.firstName ?: ""
    }
}

class ParticipantLastNameStringFieldAdapter : StringFieldAdapter<Participant> {
    override fun invoke(model: Participant): String {
        return model.lastName ?: ""
    }
}

class ParticipantFullNameStringFieldAdapter : StringFieldAdapter<Participant> {
    override operator fun invoke(model: Participant): String {
        return "${model.firstName} ${model.lastName}".trim()
    }
}

class NullableParticipantFullNameStringFieldAdapter(
    private val adapter: ParticipantFullNameStringFieldAdapter
) : StringFieldAdapter<Participant?> {
    override operator fun invoke(model: Participant?): String {
        return model?.let(adapter::invoke) ?: ""
    }
}

class ParticipantClubMemberIdStringFieldAdapter(
    private val personClubMemberIdStringFieldAdapter: PersonClubMemberIdStringFieldAdapter
) : StringFieldAdapter<Participant> {
    override fun invoke(model: Participant): String {
        return model.person?.let(personClubMemberIdStringFieldAdapter::invoke) ?: ""
    }
}

class ParticipantModelAdapter(
    val signageAdapter: SignageStringFieldAdapter,
    val firstNameAdapter: ParticipantFirstNameStringFieldAdapter,
    val lastNameAdapter: ParticipantLastNameStringFieldAdapter,
    val fullNameAdapter: ParticipantFullNameStringFieldAdapter,
    val clubMemberIdAdapter: ParticipantClubMemberIdStringFieldAdapter,
    val carModelAdapter: CarModelStringFieldAdapter,
    val carColorAdapter: CarColorStringFieldAdapter
) : Adapter<ParticipantModelAdapter.Input, ParticipantModel> {
    override fun invoke(model: Input): ParticipantModel {
        return ParticipantModel(
            participant = model.participant,
            event = model.event,
            adapter = this
        )
    }

    operator fun invoke(participant: Participant, event: Event) = invoke(Input(participant, event))

    class Input(
        val participant: Participant,
        val event: Event
    )
}

class ParticipantCollectionModelAdapter(
    private val participantAdapter: ParticipantModelAdapter
) : Adapter<ParticipantCollectionModelAdapter.Input, ParticipantCollectionModel> {

    override fun invoke(model: Input): ParticipantCollectionModel {
        return ParticipantCollectionModel(
            items = model.participants.map { participantAdapter(it, model.event) }
        )
    }

    operator fun invoke(participants: Collection<Participant>, event: Event) = invoke(Input(participants, event))

    class Input(
        val participants: Collection<Participant>,
        val event: Event
    )
}