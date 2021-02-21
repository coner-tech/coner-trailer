package org.coner.trailer.client.motorsportreg

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthenticationInterceptor private constructor(
    useCredentials: MotorsportRegBasicCredentials? = null,
    useCredentialsSupplier: (() -> MotorsportRegBasicCredentials)? = null
) : Interceptor {

    constructor(credentials: MotorsportRegBasicCredentials) : this(useCredentials = credentials)
    constructor(credentialsSupplier: () -> MotorsportRegBasicCredentials) : this(useCredentialsSupplier = credentialsSupplier)

    private val credentials: MotorsportRegBasicCredentials by lazy {
        useCredentials ?: useCredentialsSupplier?.invoke()
        ?: throw IllegalStateException("No MotorsportReg credentials")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", Credentials.basic(credentials.username, credentials.password))
            .header("X-Organization-Id", credentials.organizationId)
            .build()
        return chain.proceed(request)
    }
}