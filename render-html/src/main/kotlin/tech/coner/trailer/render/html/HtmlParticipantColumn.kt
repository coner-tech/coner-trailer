package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Participant
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.Renderer

abstract class HtmlParticipantColumn : Renderer {

    abstract val header: TR.() -> Unit

    abstract val data: TR.(Participant) -> Unit

    class Name : HtmlParticipantColumn() {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("name")
                scope = ThScope.col
                text("Name")
            }
        }
        override val data: TR.(Participant) -> Unit = {
            td {
                classes = setOf("name")
                text(renderName(it))
            }
        }
    }
    class Signage : HtmlParticipantColumn() {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("signage")
                scope = ThScope.col
                text("Signage")
            }
        }
        override val data: TR.(Participant) -> Unit = {
            td {
                classes = setOf("signage")
                text(it.signage?.classingNumber ?: "")
            }
        }
    }
    class CarModel : HtmlParticipantColumn() {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("car-model")
                scope = ThScope.col
                text("Car Model")
            }
        }
        override val data: TR.(Participant) -> Unit = {
            td {
                classes = setOf("car-model")
                text(it.car?.model ?: "")
            }
        }
    }

}