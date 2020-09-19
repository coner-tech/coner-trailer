package org.coner.trailer.client.motorsportreg

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import okhttp3.mockwebserver.*
import org.coner.trailer.client.motorsportreg.model.memberId
import org.coner.trailer.client.motorsportreg.model.members
import org.coner.trailer.client.motorsportreg.model.response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class AuthenticatedMotorsportRegApiTest {

    lateinit var api: AuthenticatedMotorsportRegApi

    lateinit var server: MockWebServer

    var testDispatcher: Dispatcher? = null

    val username = "foo"
    val password = "bar"
    val organizationId = "fake-organization-id"

    @BeforeEach
    fun before() {
        server = MockWebServer().apply {
            dispatcher = BaseDispatcher()
            start()
        }
        api = MotorsportRegApiFactory(
                url = server.url("/").toString()
        )
                .authenticatedBasic(
                        username = username,
                        password = password,
                        organizationId = organizationId
                )
    }

    @AfterEach
    fun after() {
        testDispatcher = null
        server.shutdown()
    }

    @Test
    fun `It should get members`() {
        val mockAuthenticatedResponse = MockResponse()
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .setStatus("HTTP/1.1 200 OK")
                .setBody(this.javaClass.getResourceAsStream("/get-members-ok.json").bufferedReader().readText())
        testDispatcher = QueueDispatcher().apply {
            enqueueResponse(mockAuthenticatedResponse)
        }

        val actual = api.getMembers().execute()
        val actualRequest = server.takeRequest()

        actualRequest.requestUrl?.pathSegments

        assertThat(actual, "response").all {
            code().isEqualTo(200)
            body().isNotNull().all {
                response().members().all {
                    hasSize(2)
                    index(0).all {
                        memberId().isEqualTo("1807")
                    }
                    index(1).all {
                        memberId().isEqualTo("2019-00094")
                    }
                }
            }
        }
    }

    inner class BaseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val methodDispatcher = checkNotNull(testDispatcher) { "methodDispatcher not assigned yet" }
            return when {
                enforceBasicAuthentication(request) -> methodDispatcher.dispatch(request)
                else -> unauthorized()
            }
        }

        private fun unauthorized() = MockResponse().setStatus("HTTP/1.1 401 Unauthorized")

        private fun enforceBasicAuthentication(request: RecordedRequest): Boolean {
            val (requestUsername, requestPassword) = request.getHeader("Authorization")?.let { authorization ->
                authorization.substringAfter("Basic ").let { encoded ->
                    Base64.getDecoder().decode(encoded)
                }
            }?.decodeToString()?.split(":") ?: listOf(null, null)
            val requestOrganizationId = request.getHeader("X-Organization-Id")
            assertAll {
                assertThat(requestUsername, "request username").isEqualTo(username)
                assertThat(requestPassword, "request password").isEqualTo(password)
                assertThat(requestOrganizationId, "request organization ID").isEqualTo(organizationId)
            }
            return requestUsername == username
                    && requestPassword == password
                    && requestOrganizationId == organizationId
        }
    }
}