package tech.coner.trailer.io

import com.fasterxml.jackson.annotation.JsonIgnore

data class WebappConfiguration(
    val port: Int,
    @JsonIgnore val exploratory: Boolean,
    @JsonIgnore val wait: Boolean = true
)
