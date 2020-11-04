package org.coner.trailer.client.motorsportreg.model

data class GetMemberByIdResponse(val response: Response) {

    data class Response(val member: Member)
}