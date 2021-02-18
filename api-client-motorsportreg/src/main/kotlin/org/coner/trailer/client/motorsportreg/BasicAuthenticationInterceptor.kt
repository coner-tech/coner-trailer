package org.coner.trailer.client.motorsportreg

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor(
    private val credentials: MotorsportRegBasicCredentials
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", Credentials.basic(credentials.username, credentials.password))
            .header("X-Organization-Id", credentials.organizationId)
            .build()
        return chain.proceed(request)
    }
}