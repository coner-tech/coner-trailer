package tech.coner.trailer.toolkit.sample.fooapp.domain.entity

data class Foo(val id: Id, val name: String) {
    @JvmInline
    value class Id(val value: Int)
}
const val FOO_ID_FOO = 0
const val FOO_ID_BAR = 1
const val FOO_ID_BAZ = 2
const val FOO_ID_BAT = 3
