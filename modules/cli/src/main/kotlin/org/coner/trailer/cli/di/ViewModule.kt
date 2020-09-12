package org.coner.trailer.cli.di

import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val viewModule = DI.Module("view") {
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
}