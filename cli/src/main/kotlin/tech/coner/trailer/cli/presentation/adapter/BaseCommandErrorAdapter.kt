package tech.coner.trailer.cli.presentation.adapter

import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.entity.Error
import tech.coner.trailer.cli.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.io.service.NotFoundException
import tech.coner.trailer.io.service.ReadException
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.util.ModelValidationException

class BaseCommandErrorAdapter(
    private val global: GlobalModel
) : Adapter<Throwable, BaseCommandErrorModel> {



    override fun invoke(model: Throwable): BaseCommandErrorModel {
        return when (model) {
            is ModelValidationException -> model.toError()
            is NotFoundException -> model.toError()
            is ReadException -> model.toError()
            else -> throw model
        }
            .let { error -> BaseCommandErrorModel(error, global) }
    }

    private fun ModelValidationException.toError(): Error {
        val title = Strings.errorValidationTitle
        return if (violations.size > 1) {
            Error.Multiple(title, violations.map { it.message }, this)
        } else {
            Error.Single(title, violations.single().message, this)
        }
    }

    private fun NotFoundException.toError(): Error {
        return Error.Single(Strings.errorRecordNotFoundTitle, message ?: Strings.errorUnknownMessage, this)
    }

    private fun ReadException.toError(): Error {
        return Error.Single(
            title = Strings.errorReadTitle,
            message = messageAndCauseMessage ?: Strings.errorReadMessageSuffix,
            cause = this
        )
    }

    private val Exception.messageAndCauseMessage: String?
        get() = buildString {
            message?.let(::append)
            append(" ")
            cause?.message?.let(::append)
        }
            .trim()
            .takeIf { it.isNotBlank() }
}