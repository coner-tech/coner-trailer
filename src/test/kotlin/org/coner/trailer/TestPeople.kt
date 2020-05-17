package org.coner.trailer

object TestPeople {

    // Fictional names only

    val DOMINIC_ROGERS = factory("Dominic Rogers")
    val BRANDY_HUFF = factory("Brandy Huff")
    val BRYANT_MORAN = factory("Bryant Moran")
    val REBECCA_JACKSON = factory("Rebecca Jackson")
    val JIMMY_MCKENZIE = factory("Jimmy Mckenzie")
    val EUGENE_DRAKE = factory("Eugene Drake")
    val TERI_POTTER = factory("Teri Potter")
    val HARRY_WEBSTER = factory("Harry Webster")
    val NORMAN_ROBINSON = factory("Norman Robinson")
    val JOHNNIE_ROWE = factory("Johnnie Rowe")

    private fun factory(name: String): Person {
        return Person(
                memberId = name.toLowerCase().replace(oldChar = ' ', newChar = '-'),
                name = name
        )
    }
}