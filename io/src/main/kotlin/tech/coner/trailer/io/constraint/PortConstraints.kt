package tech.coner.trailer.io.constraint

class PortConstraints : Constraint<Int>() {

    override fun assess(candidate: Int) {
        withinValidRange(candidate)
    }

    val withinValidRange = constraint(
        { port: Int -> port in 0..65535 },
        { "Port is not in valid range" }
    )
}