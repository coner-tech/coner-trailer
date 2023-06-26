package tech.coner.trailer.io.util

import java.util.*

class PropertiesReaderFactory(
    private val propertyFileName: String
) {

    fun create(): PropertiesReader {
        return javaClass.classLoader
            .getResourceAsStream(propertyFileName)
            .use { Properties().apply { load(it) } }
            .let { PropertiesReader(it) }
    }
}

class PropertiesReader(
    private val properties: Properties
) {
    fun read(property: String): String {
        return properties.getProperty(property)
    }
}