package org.coner.trailer.client.motorsportreg.model

import org.coner.trailer.TestPeople
import org.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper
import java.util.*

object TestMembers {

    private val mapper = MotorsportRegPersonMapper()

    val DOMINIC_ROGERS by lazy { mapper.fromCore(TestPeople.DOMINIC_ROGERS)!! }
    val BRANDY_HUFF by lazy { mapper.fromCore(TestPeople.BRANDY_HUFF)!! }
    val BRYANT_MORAN by lazy { mapper.fromCore(TestPeople.BRYANT_MORAN)!! }
    val REBECCA_JACKSON by lazy { mapper.fromCore(TestPeople.REBECCA_JACKSON)!! }

}