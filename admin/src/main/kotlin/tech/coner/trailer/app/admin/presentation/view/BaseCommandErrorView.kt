package tech.coner.trailer.app.admin.presentation.view

import com.github.ajalt.mordant.markdown.Markdown
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.app.admin.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.presentation.text.view.TextView

class BaseCommandErrorView(
    private val terminal: Terminal,
    private val lineSeparator: String
) : TextView<BaseCommandErrorModel> {

    private val errorPrimary = TextStyles.bold + TextColors.red

    override fun invoke(model: BaseCommandErrorModel): String {
        return buildString {
            append(errorPrimary("${model.prefix}:"))
            when (model.messageStyle) {
                BaseCommandErrorModel.MessageStyle.SINGLE_MESSAGE -> {
                    append(" ")
                    appendLine(model.singleMessage)
                }

                BaseCommandErrorModel.MessageStyle.MULTIPLE_MESSAGES -> {
                    appendLine()
                    appendLine(
                        terminal.render(
                            Markdown(
                                model.multipleMessages
                                    .mapIndexed { index, message ->
                                        "${index + 1}. $message"
                                    }
                                    .joinToString(separator = lineSeparator)
                            )
                        )
                    )
                }
            }

            model.stackTrace?.also {
                appendLine()
                appendLine("Stack Trace:")
                appendLine(it)
            }
        }

    }
}