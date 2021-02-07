package org.coner.trailer.cli.util

class NativeImageCommandArrayFactory : ProcessCommandArrayFactory {

    override fun build(): Array<String> {
        return fromSystemProperty()
            ?: throw IllegalStateException("Unable to build native image argument.")
    }

    private fun fromSystemProperty(): Array<String>? = try {
        val nativeImage = System.getProperty("coner-trailer-cli.native-image")
        arrayOf(nativeImage)
    } catch (t: Throwable) {
        null
    }
}