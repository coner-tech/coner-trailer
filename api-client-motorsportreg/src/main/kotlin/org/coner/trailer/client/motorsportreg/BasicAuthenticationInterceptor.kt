package org.coner.trailer.client.motorsportreg

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor(
        private val username: String,
        private val password: String,
        private val organizationId: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .header("Authorization", Credentials.basic(username, password))
                .header("X-Organization-Id", organizationId)
                .build()
        return chain.proceed(request)
    }
}