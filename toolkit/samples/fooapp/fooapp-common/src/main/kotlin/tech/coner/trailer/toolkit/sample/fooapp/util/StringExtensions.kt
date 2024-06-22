package tech.coner.trailer.toolkit.sample.fooapp.util

fun String.capitalizeFirstChar() = when (length) {
    0 -> this
    1 -> uppercase()
    else -> "${this[0].uppercase()}${substring(1)}"
}