package tech.coner.trailer.assertk.ktor

import assertk.Assert
import assertk.assertions.prop
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking

fun Assert<HttpResponse>.status() = prop(HttpResponse::status)
fun Assert<HttpResponse>.bodyAsText() = transform("bodyAsText") { runBlocking { it.bodyAsText() } }

fun Assert<HttpStatusCode>.isSuccess() = transform("isSuccess") { it.isSuccess() }