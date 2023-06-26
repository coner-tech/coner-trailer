package tech.coner.trailer.assertk.ktor

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
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

fun Assert<HttpResponse>.bodyAsText() = transform("bodyAsText") { runBlocking { it.bodyAsText() } }
fun Assert<HttpResponse>.contentType() = transform("contentType") { it.contentType() }
fun Assert<HttpResponse>.hasMainContentType(expected: ContentType) = contentType().isNotNull().mainContentType().isEqualTo(expected)
fun Assert<HttpResponse>.hasContentTypeIgnoringParams(expected: ContentType) = contentType()
        .isNotNull()
        .transform { ContentType(it.contentType, it.contentSubtype) }
        .isEqualTo(expected)
fun Assert<HttpResponse>.status() = prop(HttpResponse::status)

fun Assert<HttpStatusCode>.isSuccess() = transform("isSuccess") { it.isSuccess() }.isTrue()