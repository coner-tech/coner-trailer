package org.coner.trailer.cli.util

class NativeImageArgumentFactory : ProcessArgumentFactory {

    override fun build(): Array<String> {
        fun fromSystemProperties(): Array<String>? = try {
            val nativeImage = System.getProperty("coner-trailer-cli.native-image")
            arrayOf(nativeImage)
        } catch (t: Throwable) {
            null
        }
        return fromSystemProperties()
            ?: throw IllegalStateException("Unable to build native image argument.")
    }
}