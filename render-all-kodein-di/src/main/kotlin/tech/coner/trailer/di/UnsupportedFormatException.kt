package tech.coner.trailer.di

import tech.coner.trailer.render.Renderer
import kotlin.reflect.KClass



class UnsupportedFormatException(format: Format, type: KClass<*>) : Throwable(
    message = "${type.simpleName} does not support ${format.name}"
)