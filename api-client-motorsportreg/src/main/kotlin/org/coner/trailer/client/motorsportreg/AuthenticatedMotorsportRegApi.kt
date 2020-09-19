package org.coner.trailer.client.motorsportreg

import org.coner.trailer.client.motorsportreg.model.GetMembersResponse
import retrofit2.Call
import retrofit2.http.GET

interface AuthenticatedMotorsportRegApi {

    @GET("/rest/members.json")
    fun getMembers(): Call<GetMembersResponse>
}