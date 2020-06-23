package org.coner.trailer

object TestPeople {

    // Fictional names only

    val DOMINIC_ROGERS by lazy { factory("Dominic Rogers") }
    val BRANDY_HUFF by lazy { factory("Brandy Huff") }
    val BRYANT_MORAN by lazy { factory("Bryant Moran") }
    val REBECCA_JACKSON by lazy { factory("Rebecca Jackson") }
    val JIMMY_MCKENZIE by lazy { factory("Jimmy Mckenzie") }
    val EUGENE_DRAKE by lazy { factory("Eugene Drake") }
    val TERI_POTTER by lazy { factory("Teri Potter") }
    val HARRY_WEBSTER by lazy { factory("Harry Webster") }
    val NORMAN_ROBINSON by lazy { factory("Norman Robinson") }
    val JOHNNIE_ROWE by lazy { factory("Johnnie Rowe") }

    private fun factory(name: String): Person {
        return Person(
                memberId = name.toLowerCase().replace(oldChar = ' ', newChar = '-'),
                name = name
        )
    }
}