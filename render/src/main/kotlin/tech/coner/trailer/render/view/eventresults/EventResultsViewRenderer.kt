package tech.coner.trailer.render.view.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.render.Renderer

interface EventResultsViewRenderer<ER : EventResults> : Renderer<ER>
