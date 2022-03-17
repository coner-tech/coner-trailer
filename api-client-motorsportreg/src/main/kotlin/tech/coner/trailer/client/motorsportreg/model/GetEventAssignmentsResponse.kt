package tech.coner.trailer.client.motorsportreg.model

data class GetEventAssignmentsResponse(val response: Response) {

    data class Response(
        val assignments: List<Assignment>
    )
}
