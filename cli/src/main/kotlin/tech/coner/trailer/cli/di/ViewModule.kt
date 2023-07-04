package tech.coner.trailer.cli.di

import com.github.ajalt.clikt.output.defaultCliktConsole
import org.kodein.di.*
import tech.coner.trailer.Event
import tech.coner.trailer.cli.view.*
import tech.coner.trailer.cli.view.mordant.MordantEventTableView

val viewModule = DI.Module("coner.trailer.cli.view") {
    bindInstance { defaultCliktConsole() }
    bind<ClubView>() with provider { ClubView() }
    bind<DatabaseConfigurationView>() with provider { DatabaseConfigurationView(
        console = instance()
    ) }
    bind<EventPointsCalculatorView>() with provider { EventPointsCalculatorView(
        console = instance()
    ) }
    bind<RankingSortView>() with provider { RankingSortView(
        console = instance()
    ) }
    bind<SeasonPointsCalculatorConfigurationView>() with provider { SeasonPointsCalculatorConfigurationView(
        console = instance()
    ) }

    bind<PersonTableView>() with provider { PersonTableView(asciiTableFactory = provider()) }
    bind<MotosportRegMemberView>() with provider { MotosportRegMemberView(console = instance()) }
    bind<MotorsportRegMemberTableView>() with provider { MotorsportRegMemberTableView(asciiTableFactory = provider()) }
    bind<SeasonView>() with provider { SeasonView() }
    bind<SeasonTableView>() with provider { SeasonTableView(asciiTableFactory = provider()) }
    bind<PolicyView>() with provider { PolicyView(console = instance()) }
    bind<EventView>() with provider { EventView(asciiTableFactory = provider()) }
    bind<View<List<Event>>>() with provider { MordantEventTableView(terminal = instance()) }
    bind<CrispyFishRegistrationView>() with provider { CrispyFishRegistrationView() }
    bind<CrispyFishRegistrationTableView>() with provider { CrispyFishRegistrationTableView(asciiTableFactory = provider()) }
    bind<PeopleMapKeyTableView>() with provider { PeopleMapKeyTableView(asciiTableFactory = provider()) }
    bindProvider { WebappConfigurationView() }
}