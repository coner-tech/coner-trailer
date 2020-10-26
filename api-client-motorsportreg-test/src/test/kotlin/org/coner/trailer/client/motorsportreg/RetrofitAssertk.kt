package org.coner.trailer.client.motorsportreg

import assertk.Assert
import assertk.assertions.prop
import retrofit2.Response

fun <T> Assert<Response<T>>.code() = prop("code") { it.code() }
fun <T> Assert<Response<T>>.body() = prop("body") { it.body() }