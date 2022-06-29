package tech.coner.trailer.client.motorsportreg.model

import tech.coner.trailer.TestPeople
import tech.coner.trailer.datasource.motorsportreg.mapper.MotorsportRegPersonMapper

object TestMembers {

    private val mapper = MotorsportRegPersonMapper()

    val DOMINIC_ROGERS by lazy { tech.coner.trailer.client.motorsportreg.model.TestMembers.mapper.fromCore(TestPeople.DOMINIC_ROGERS)!! }
    val BRANDY_HUFF by lazy { tech.coner.trailer.client.motorsportreg.model.TestMembers.mapper.fromCore(TestPeople.BRANDY_HUFF)!! }
    val BRYANT_MORAN by lazy { tech.coner.trailer.client.motorsportreg.model.TestMembers.mapper.fromCore(TestPeople.BRYANT_MORAN)!! }
    val REBECCA_JACKSON by lazy { tech.coner.trailer.client.motorsportreg.model.TestMembers.mapper.fromCore(TestPeople.REBECCA_JACKSON)!! }

}