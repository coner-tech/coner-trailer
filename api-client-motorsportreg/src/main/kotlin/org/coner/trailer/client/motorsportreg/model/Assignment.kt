package org.coner.trailer.client.motorsportreg.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Assignment(
    val id: String,
    val status: String,
    val attendeeId: String,
    val firstName: String,
    val lastName: String,
    val region: String,
    val sponsor: String,
    @JsonProperty("vehicleuri") val vehicleUri: String,
    val color: String,
    val year: String,
    val make: String,
    val model: String,
    val tireBrand: String,
    @JsonProperty("classModifier") val classModifierLong: String,
    val classModifierShort: String,
    val classModifierId: String,
    @JsonProperty("class") val classLong: String,
    val classShort: String,
    val classId: String,
    val segment: String,
    @JsonProperty("segmenturi") val segmentUri: String,
    @JsonProperty("profileuri") val profileUri: String,
    @JsonProperty("memberId") val clubMemberId: String,
    @JsonProperty("memberuri") val motorsportRegMemberUri: String
)
