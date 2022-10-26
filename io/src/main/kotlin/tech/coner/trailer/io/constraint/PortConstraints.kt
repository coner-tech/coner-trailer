package tech.coner.trailer.io.constraint

class PortConstraints : Constraint<Int>() {

    override fun assess(candidate: Int) {
        inValidRange(candidate)
    }

    val inValidRange = constraint(
        { port: Int -> port in 0..65535 },
        { "Port is not in valid range" }
    )
}