package tech.coner.trailer.cli.presentation.model

import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.entity.Error
import tech.coner.trailer.presentation.model.Model

class BaseCommandErrorModel(
    private val error: Error,
    private val global: GlobalModel
) : Model {

    val prefix: String
        get() = error.title

    val messageStyle: MessageStyle
        get() = when (error) {
            is Error.Multiple -> MessageStyle.MULTIPLE_MESSAGES
            is Error.Single -> MessageStyle.SINGLE_MESSAGE
        }

    val multipleMessages: List<String>
        get() = when (error) {
            is Error.Multiple -> error.messages
            else -> throw IllegalStateException("Program error: multipleMessages is only available when error type is multiple")
        }

    val singleMessage: String
        get() = when (error) {
            is Error.Single -> error.message
            else -> throw IllegalStateException("Program error: singleMessage is only available when error type is single")
        }

    val stackTrace: String?
        get() = if (global.verbose) error.cause?.stackTraceToString() else null

    enum class MessageStyle {
        SINGLE_MESSAGE,
        MULTIPLE_MESSAGES
    }
}

