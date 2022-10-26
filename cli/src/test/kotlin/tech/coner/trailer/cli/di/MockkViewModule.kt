package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.view.CrispyFishRegistrationView
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.cli.view.WebappConfigurationView

val mockkViewModule = DI.Module("tech.coner.trailer.cli.mockkViews") {
    bindSingleton<CrispyFishRegistrationView> { mockk() }
    bindSingleton<DatabaseConfigurationView> { mockk() }
    bindSingleton<EventView> { mockk() }
    bindSingleton<EventPointsCalculatorView> { mockk() }
    bindSingleton<PolicyView> { mockk() }
    bindSingleton<RankingSortView> { mockk() }
    bindSingleton<WebappConfigurationView> { mockk() }
}