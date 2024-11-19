package tech.coner.trailer.domain.entity

data class Club(
    val name: String
) {
    companion object {
        const val NAME_MAX_LENGTH = 100
    }
}