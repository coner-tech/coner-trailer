package tech.coner.trailer.render.property

import tech.coner.trailer.Person

fun interface PersonIdPropertyRenderer: PropertyRenderer<Person>
fun interface PersonFirstNamePropertyRenderer: PropertyRenderer<Person>
fun interface PersonLastNamePropertyRenderer: PropertyRenderer<Person>
fun interface PersonClubMemberIdPropertyRenderer: PropertyRenderer<Person>
fun interface PersonMotorsportRegMemberIdPropertyRenderer: PropertyRenderer<Person>