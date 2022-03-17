package tech.coner.trailer.io.service

import tech.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import tech.coner.trailer.client.motorsportreg.model.Assignment

class MotorsportRegEventService(
    private val authenticatedApi: AuthenticatedMotorsportRegApi
) {

    fun fetchAssignments(eventId: String): List<Assignment> {
        val response = authenticatedApi.getEventAssignments(eventId = eventId).execute().also {
            check(it.isSuccessful) { "Failed to fetch assignments. ${it.code()} ${it.message()}" }
        }
        return checkNotNull(response.body()) { "Got assignments with null body" }
            .response
            .assignments
    }
}