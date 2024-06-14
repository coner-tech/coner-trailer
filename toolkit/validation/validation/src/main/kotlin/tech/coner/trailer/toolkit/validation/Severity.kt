package tech.coner.trailer.toolkit.validation

enum class Severity(val valid: Boolean) {
    Error(false),
    Warning(true),
    Success(true),
    Info(true)
}