package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.CrispyFishRegistrationView
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.cli.view.EventTableView
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.cli.view.MotorsportRegMemberTableView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.cli.view.PersonTableView
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.cli.view.WebappConfigurationView
import tech.coner.trailer.io.TestEnvironments.mock

val mockkViewModule = DI.Module("mockk for tech.coner.trailer.cli.view") {
    bindSingleton<CrispyFishRegistrationView> { mockk() }
    bindSingleton<CrispyFishRegistrationTableView> { mockk() }
    bindSingleton<DatabaseConfigurationView> { mockk() }
    bindSingleton<EventView> { mockk() }
    bindSingleton<EventTableView> {  mockk() }
    bindSingleton<EventPointsCalculatorView> { mockk() }
    bindSingleton<MotorsportRegMemberTableView> { mockk() }
    bindSingleton<PeopleMapKeyTableView> { mockk() }
    bindSingleton<PersonTableView> { mockk() }
    bindSingleton<PersonView> { mockk() }
    bindSingleton<PolicyView> { mockk() }
    bindSingleton<RankingSortView> { mockk() }
    bindSingleton<SeasonPointsCalculatorConfigurationView> { mockk() }
    bindSingleton<SeasonTableView> { mockk() }
    bindSingleton<SeasonView> { mockk() }
    bindSingleton<WebappConfigurationView> { mockk() }
}