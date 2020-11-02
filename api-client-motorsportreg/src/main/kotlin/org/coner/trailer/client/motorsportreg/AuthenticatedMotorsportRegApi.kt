package org.coner.trailer.client.motorsportreg

import org.coner.trailer.client.motorsportreg.model.GetMemberByIdResponse
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthenticatedMotorsportRegApi {

    @GET("/rest/members.json")
    fun getMembers(): Call<GetMembersResponse>

    @GET("/rest/members/{id}.json")
    fun getMemberById(@Query("id") id: String): Call<GetMemberByIdResponse>
}