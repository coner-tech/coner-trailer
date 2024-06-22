package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen

import kotlin.random.Random

sealed class DmvAppScreen {
    data class DriversLicenseApplication(private val index: Int = Random.nextInt()) : DmvAppScreen()
    data object Settings: DmvAppScreen()
}