package tech.coner.trailer.toolkit.sample.dmvapp.domain.service

import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicenseApplication

interface DriversLicenseApplicationService {

    fun process(application: DriversLicenseApplication): DriversLicenseApplication.Outcome


}