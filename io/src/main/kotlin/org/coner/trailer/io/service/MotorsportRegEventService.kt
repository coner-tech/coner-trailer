package org.coner.trailer.io.service

import org.coner.trailer.client.motorsportreg.AuthenticatedMotorsportRegApi
import org.coner.trailer.client.motorsportreg.model.Assignment

class MotorsportRegEventService(
    private val api: AuthenticatedMotorsportRegApi
) {

    fun fetchAssignments(eventId: String): List<Assignment> {
        val response = api.getEventAssignments(eventId = eventId).execute().also {
            check(it.isSuccessful) { "Failed to fetch assignments. ${it.code()} ${it.message()}" }
        }
        return checkNotNull(response.body()) { "Got assignments with null body" }
            .response
            .assignments
    }
}