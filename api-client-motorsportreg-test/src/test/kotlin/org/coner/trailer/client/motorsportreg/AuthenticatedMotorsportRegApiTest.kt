package org.coner.trailer.client.motorsportreg

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import okhttp3.mockwebserver.*
import org.coner.trailer.TestPeople
import org.coner.trailer.client.motorsportreg.model.*
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
                getMembersResponse().members().all {
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

    @Test
    fun `It should get member by ID`() {
        val mockAuthenticatedResponse = MockResponse()
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .setStatus("HTTP/1.1 200 OK")
                .setBody(this.javaClass.getResourceAsStream("/get-member-by-id-ok.json").bufferedReader().readText())
        testDispatcher = QueueDispatcher().apply {
            enqueueResponse(mockAuthenticatedResponse)
        }

        val actual = api.getMemberById(TestMembers.REBECCA_JACKSON.id).execute()
        val actualRequest = server.takeRequest()

        actualRequest.requestUrl?.pathSegments

        assertThat(actual, "response").all {
            code().isEqualTo(200)
            body().isNotNull().all {
                getMemberByIdResponse().member().memberId().isEqualTo("1807")
            }
        }
    }

    inner class BaseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val methodDispatcher = checkNotNull(testDispatcher) { "methodDispatcher not assigned yet" }
            return when {
                request.hasValidBasicAuthentication() -> methodDispatcher.dispatch(request)
                else -> unauthorized()
            }
        }

        private fun unauthorized() = MockResponse().setStatus("HTTP/1.1 401 Unauthorized")

        private fun RecordedRequest.hasValidBasicAuthentication(): Boolean {
            val (requestUsername, requestPassword) = getHeader("Authorization")?.let { authorization ->
                authorization.substringAfter("Basic ").let { encoded ->
                    Base64.getDecoder().decode(encoded)
                }
            }?.decodeToString()?.split(":") ?: listOf(null, null)
            val requestOrganizationId = getHeader("X-Organization-Id")
            return requestUsername == username
                    && requestPassword == password
                    && requestOrganizationId == organizationId
        }
    }
}