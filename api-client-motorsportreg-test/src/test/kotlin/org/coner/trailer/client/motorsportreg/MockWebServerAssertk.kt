package org.coner.trailer.client.motorsportreg

import assertk.Assert
import assertk.assertions.prop
import okhttp3.mockwebserver.RecordedRequest

fun Assert<RecordedRequest>.requestUrl() = prop("request URL") { it.requestUrl }
