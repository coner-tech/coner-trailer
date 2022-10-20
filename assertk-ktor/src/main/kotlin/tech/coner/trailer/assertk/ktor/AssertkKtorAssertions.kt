package tech.coner.trailer.assertk.ktor

import assertk.Assert
import assertk.assertions.prop
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

fun Assert<HttpResponse>.status() = prop(HttpResponse::status)
fun Assert<HttpResponse>.bodyAsText() = transform("bodyAsText") { runBlocking { it.bodyAsText() } }

fun Assert<HttpStatusCode>.isSuccess() = transform("isSuccess") { it.isSuccess() }