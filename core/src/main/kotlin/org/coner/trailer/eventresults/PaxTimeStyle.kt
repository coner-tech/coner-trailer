package org.coner.trailer.eventresults

enum class PaxTimeStyle {
    /**
     * Calculate pax time results fairly with the formula:
     * (scratch time * pax factor) + penalty
     * Use standard half-up rounding
     */
    FAIR,

    /**
     * Reproduces bugs in legacy crispy fish results. This is necessary in
     * order to accurately recreate historical results without impacting
     * historical outcomes.
     *
     * Bug #1: Scales entire penalized time by pax factor.
     * This varies the impact of cone penalties depending on the pax factor,
     * which means the softer the pax factor, the softer the cone penalty.
     * Formula:
     * (scratch time + penalty) * pax factor
     *
     * Bug #2: Bizarre rounding behavior. Examples:
     * 0.000500 => 0.000
     * 0.000800 => 0.000
     * 0.000980 => 0.001
     *
     */
    LEGACY_BUGGED
}