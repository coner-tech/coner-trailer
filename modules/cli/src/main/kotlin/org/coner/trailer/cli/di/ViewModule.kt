package org.coner.trailer.cli.di

import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.cli.view.RankingSortView
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val viewModule = DI.Module("view") {
    bind<DatabaseConfigurationView>() with provider { DatabaseConfigurationView(
            console = instance()
    ) }
    bind<ParticipantEventResultPointsCalculatorView>() with provider { ParticipantEventResultPointsCalculatorView(
            console = instance()
    ) }
    bind<RankingSortView>() with provider { RankingSortView(
            console = instance()
    ) }
}