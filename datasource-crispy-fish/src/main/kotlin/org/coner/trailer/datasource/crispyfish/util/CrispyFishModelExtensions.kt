package org.coner.trailer.datasource.crispyfish.util

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.StagingLineRegistration

fun Registration.syntheticSignageKey() = "$category$handicap $number"
fun StagingLineRegistration.syntheticSignageKey() = "$classing $number"