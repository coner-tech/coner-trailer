package org.coner.trailer.io.service

import org.coner.crispyfish.model.Registration

interface EventCrispyFishForcePersonVerificationFailureCallback {
    fun onRegistrationWithoutMemberNumber(registration: Registration)
    fun onPersonWithClubMemberIdNotFound(registration: Registration)
    fun onMultiplePeopleWithClubMemberIdFound(registration: Registration)
}