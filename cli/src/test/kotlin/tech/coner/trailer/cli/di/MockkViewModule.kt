package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.Event
import tech.coner.trailer.cli.view.*
import tech.coner.trailer.render.text.view.TextPersonViewRenderer

val mockkViewModule = DI.Module("mockk for tech.coner.trailer.cli.view") {
    bindSingleton<CrispyFishRegistrationView> { mockk() }
    bindSingleton<CrispyFishRegistrationTableView> { mockk() }
    bindSingleton<DatabaseConfigurationView> { mockk() }
    bindSingleton<EventView> { mockk() }
    bindSingleton<View<List<Event>>> { mockk() }
    bindSingleton<EventPointsCalculatorView> { mockk() }
    bindSingleton<MotorsportRegMemberTableView> { mockk() }
    bindSingleton<PeopleMapKeyTableView> { mockk() }
    bindSingleton<TextPersonViewRenderer> { mockk() }
    bindSingleton<PolicyView> { mockk() }
    bindSingleton<RankingSortView> { mockk() }
    bindSingleton<SeasonPointsCalculatorConfigurationView> { mockk() }
    bindSingleton<SeasonTableView> { mockk() }
    bindSingleton<SeasonView> { mockk() }
    bindSingleton<WebappConfigurationView> { mockk() }
}