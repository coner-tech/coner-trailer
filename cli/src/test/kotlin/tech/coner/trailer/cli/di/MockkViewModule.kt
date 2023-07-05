package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.CrispyFishRegistrationView
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.cli.view.MotorsportRegMemberTableView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.cli.view.WebappConfigurationView

val mockkViewModule = DI.Module("mockk for tech.coner.trailer.cli.view") {
    bindSingleton<CrispyFishRegistrationView> { mockk() }
    bindSingleton<CrispyFishRegistrationTableView> { mockk() }
    bindSingleton<DatabaseConfigurationView> { mockk() }
    bindSingleton<EventPointsCalculatorView> { mockk() }
    bindSingleton<MotorsportRegMemberTableView> { mockk() }
    bindSingleton<PeopleMapKeyTableView> { mockk() }
    bindSingleton<RankingSortView> { mockk() }
    bindSingleton<SeasonPointsCalculatorConfigurationView> { mockk() }
    bindSingleton<SeasonTableView> { mockk() }
    bindSingleton<SeasonView> { mockk() }
    bindSingleton<WebappConfigurationView> { mockk() }
}