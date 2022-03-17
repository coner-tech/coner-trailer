package tech.coner.trailer.client.motorsportreg

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import tech.coner.trailer.client.motorsportreg.model.GetEventAssignmentsResponse
import tech.coner.trailer.client.motorsportreg.model.GetMemberByIdResponse
import tech.coner.trailer.client.motorsportreg.model.GetMembersResponse

interface AuthenticatedMotorsportRegApi {

    @GET("/rest/members.json")
    fun getMembers(): Call<GetMembersResponse>

    @GET("/rest/members/{id}.json")
    fun getMemberById(@Path("id") id: String): Call<GetMemberByIdResponse>

    @GET("/rest/events/{eventId}/assignments.json")
    fun getEventAssignments(@Path("eventId") eventId: String): Call<GetEventAssignmentsResponse>
}