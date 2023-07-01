package tech.coner.trailer.render.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.render.Renderer
import tech.coner.trailer.render.Text

interface EventResultsRenderer<ER : EventResults> : Renderer<ER>
