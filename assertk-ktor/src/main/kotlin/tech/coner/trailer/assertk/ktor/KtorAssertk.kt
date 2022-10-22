package tech.coner.trailer.assertk.ktor

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import assertk.assertions.prop
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

fun Assert<ContentType>.mainContentType() = prop(ContentType::contentType)
fun Assert<ContentType>.hasSameMainContentType(expected: ContentType) = mainContentType().isEqualTo(expected.contentType)

fun Assert<HttpResponse>.bodyAsText() = transform("bodyAsText") { runBlocking { it.bodyAsText() } }
fun Assert<HttpResponse>.contentType() = transform("contentType") { it.contentType() }
fun Assert<HttpResponse>.status() = prop(HttpResponse::status)

fun Assert<HttpStatusCode>.isSuccess() = transform("isSuccess") { it.isSuccess() }.isTrue()