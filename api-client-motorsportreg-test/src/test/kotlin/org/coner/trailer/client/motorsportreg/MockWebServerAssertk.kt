package org.coner.trailer.client.motorsportreg

import assertk.Assert
import assertk.assertions.prop
import okhttp3.HttpUrl
import okhttp3.mockwebserver.RecordedRequest

fun Assert<RecordedRequest>.requestUrl() = prop("requestUrl") { it.requestUrl }
fun Assert<HttpUrl>.pathSegments() = prop("pathSegments") { it.pathSegments }