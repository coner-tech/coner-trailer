package tech.coner.trailer.app.admin.di

import org.kodein.di.*
import tech.coner.trailer.app.admin.view.*

val viewModule = DI.Module("coner.trailer.cli.view") {
    bind<DatabaseConfigurationView>() with provider { DatabaseConfigurationView(
        terminal = instance()
    ) }
    bind<EventPointsCalculatorView>() with provider { EventPointsCalculatorView(
        terminal = instance()
    ) }
    bind<RankingSortView>() with provider { RankingSortView(
        terminal = instance()
    ) }
    bind<SeasonPointsCalculatorConfigurationView>() with provider { SeasonPointsCalculatorConfigurationView(
        terminal = instance()
    ) }

    bind<MotosportRegMemberView>() with provider { MotosportRegMemberView(terminal = instance()) }
    bind<MotorsportRegMemberTableView>() with provider { MotorsportRegMemberTableView(asciiTableFactory = provider()) }
    bind<SeasonView>() with provider { SeasonView() }
    bind<SeasonTableView>() with provider { SeasonTableView(asciiTableFactory = provider()) }
    bind<CrispyFishRegistrationView>() with provider { CrispyFishRegistrationView() }
    bind<CrispyFishRegistrationTableView>() with provider { CrispyFishRegistrationTableView(asciiTableFactory = provider()) }
    bind<PeopleMapKeyTableView>() with provider { PeopleMapKeyTableView(asciiTableFactory = provider()) }
    bindProvider { WebappConfigurationView() }
}