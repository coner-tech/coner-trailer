package tech.coner.trailer

/**
 * Defines how to present Signage
 */
enum class SignageStyle {
    /**
     * Signage will be presented with classing first, number second
     *
     * Example: CS 1
      */
    CLASSING_NUMBER,

    /**
     * Signage will be presented with number first, classing second
     *
     * Example: 1 CS
     */
    NUMBER_CLASSING
}