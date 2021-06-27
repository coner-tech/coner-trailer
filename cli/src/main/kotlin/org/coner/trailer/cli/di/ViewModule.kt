package org.coner.trailer.cli.di

import org.coner.trailer.cli.view.*
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlEventResultsReportColumnRendererFactory
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlGroupedResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.render.standardEventResultsReportColumns
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val viewModule = DI.Module("coner.trailer.cli.view") {
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
    bind<PersonView>() with provider { PersonView(
            console = instance()
    ) }
    bind<PersonTableView>() with provider { PersonTableView() }
    bind<MotosportRegMemberView>() with provider { MotosportRegMemberView(console = instance()) }
    bind<MotorsportRegMemberTableView>() with provider { MotorsportRegMemberTableView() }
    bind<SeasonView>() with provider { SeasonView() }
    bind<SeasonTableView>() with provider { SeasonTableView() }
    bind<PolicyView>() with provider { PolicyView() }
    bind<EventView>() with provider { EventView() }
    bind<EventTableView>() with provider { EventTableView() }
    bind<CrispyFishRegistrationView>() with provider { CrispyFishRegistrationView() }
    bind<CrispyFishRegistrationTableView>() with provider { CrispyFishRegistrationTableView() }
    bind<PeopleMapKeyTableView>() with provider { PeopleMapKeyTableView() }
    bind<KotlinxHtmlOverallResultsReportRenderer>() with provider { KotlinxHtmlOverallResultsReportRenderer(
        columns = KotlinxHtmlEventResultsReportColumnRendererFactory()
            .factory(standardEventResultsReportColumns)
    ) }
    bind<KotlinxHtmlGroupedResultsReportRenderer>() with provider { KotlinxHtmlGroupedResultsReportRenderer(
        columns = KotlinxHtmlEventResultsReportColumnRendererFactory()
            .factory(standardEventResultsReportColumns)
    ) }
}