package tech.coner.trailer.cli.service

import tech.coner.trailer.cli.Feature
import tech.coner.trailer.io.util.PropertiesReader

class FeatureService(
    private val propertiesReader: PropertiesReader
) {

    fun get(): Set<Feature> {
        return buildSet {
        }
    }
}