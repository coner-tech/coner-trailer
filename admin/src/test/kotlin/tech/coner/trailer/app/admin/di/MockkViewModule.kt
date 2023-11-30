package tech.coner.trailer.app.admin.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.app.admin.view.CrispyFishRegistrationTableView
import tech.coner.trailer.app.admin.view.CrispyFishRegistrationView
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
import tech.coner.trailer.app.admin.view.EventPointsCalculatorView
import tech.coner.trailer.app.admin.view.MotorsportRegMemberTableView
import tech.coner.trailer.app.admin.view.PeopleMapKeyTableView
import tech.coner.trailer.app.admin.view.RankingSortView
import tech.coner.trailer.app.admin.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.app.admin.view.SeasonTableView
import tech.coner.trailer.app.admin.view.SeasonView
import tech.coner.trailer.app.admin.view.WebappConfigurationView

val mockkViewModule = DI.Module("mockk for tech.coner.trailer.app.admin.view") {
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