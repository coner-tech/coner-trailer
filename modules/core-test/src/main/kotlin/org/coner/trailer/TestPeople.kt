package org.coner.trailer

object TestPeople {

    // Fictional names only

    val DOMINIC_ROGERS by lazy { factory("Dominic Rogers", "2019-00061") }
    val BRANDY_HUFF by lazy { factory("Brandy Huff", "2019-00080") }
    val BRYANT_MORAN by lazy { factory("Bryant Moran", "2019-00125") }
    val REBECCA_JACKSON by lazy { factory("Rebecca Jackson", "1807") }
    val ANASTASIA_RIGLER by lazy { factory("Anastasia Rigler", "2019-00094") }
    val JIMMY_MCKENZIE by lazy { factory("Jimmy Mckenzie", "2476") }
    val EUGENE_DRAKE by lazy { factory("Eugene Drake", "2019-00057") }
    val BENNETT_PANTONE by lazy { factory("Bennett Pantone", "2019-00295") }
    val TERI_POTTER by lazy { factory("Teri Potter", "2019-00051") }
    val HARRY_WEBSTER by lazy { factory("Harry Webster", "2276") }
    val NORMAN_ROBINSON by lazy { factory("Norman Robinson") }
    val JOHNNIE_ROWE by lazy { factory("Johnnie Rowe") }

    private fun factory(name: String, memberId: String? = null): Person {
        return Person(
                memberId = memberId ?: name.toLowerCase().replace(oldChar = ' ', newChar = '-'),
                name = name
        )
    }
}