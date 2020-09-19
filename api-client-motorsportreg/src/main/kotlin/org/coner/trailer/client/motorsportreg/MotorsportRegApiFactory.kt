package org.coner.trailer.client.motorsportreg

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MotorsportRegApiFactory(
        private val url: String = MOTORSPORTREG_API_URL
) {

    fun authenticatedBasic(
            username: String,
            password: String,
            organizationId: String
    ): AuthenticatedMotorsportRegApi {
        val basicAuthenticationInterceptor = BasicAuthenticationInterceptor(
                username = username,
                password = password,
                organizationId = organizationId
        )
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(basicAuthenticationInterceptor)
                .build()
        val jacksonConverterFactory = JacksonConverterFactory.create(jacksonObjectMapper)
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(jacksonConverterFactory)
                .build()
        return retrofit.create(AuthenticatedMotorsportRegApi::class.java)
    }

    private val jacksonObjectMapper: ObjectMapper by lazy {
        ObjectMapper()
                .registerKotlinModule()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    companion object {
        const val MOTORSPORTREG_API_URL = "https://api.motorsportreg.com"
    }
}