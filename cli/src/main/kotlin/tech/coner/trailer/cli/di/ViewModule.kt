package tech.coner.trailer.cli.di

import com.github.ajalt.clikt.output.defaultCliktConsole
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindInstance
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.provider
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.CrispyFishRegistrationView
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.cli.view.MotorsportRegMemberTableView
import tech.coner.trailer.cli.view.MotosportRegMemberView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.cli.view.WebappConfigurationView

val viewModule = DI.Module("coner.trailer.cli.view") {
    bindInstance { defaultCliktConsole() }
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

    bind<MotosportRegMemberView>() with provider { MotosportRegMemberView(console = instance()) }
    bind<MotorsportRegMemberTableView>() with provider { MotorsportRegMemberTableView(asciiTableFactory = provider()) }
    bind<SeasonView>() with provider { SeasonView() }
    bind<SeasonTableView>() with provider { SeasonTableView(asciiTableFactory = provider()) }
    bind<CrispyFishRegistrationView>() with provider { CrispyFishRegistrationView() }
    bind<CrispyFishRegistrationTableView>() with provider { CrispyFishRegistrationTableView(asciiTableFactory = provider()) }
    bind<PeopleMapKeyTableView>() with provider { PeopleMapKeyTableView(asciiTableFactory = provider()) }
    bindProvider { WebappConfigurationView() }
}