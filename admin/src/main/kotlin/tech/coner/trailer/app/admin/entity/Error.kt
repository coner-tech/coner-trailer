package tech.coner.trailer.app.admin.entity

sealed class Error {
    abstract val title: String
    abstract val cause: Throwable?

    class Single(
        override val title: String,
        val message: String,
        override val cause: Throwable?
    ) : Error()
    
    class Multiple(
        override val title: String,
        val messages: List<String>,
        override val cause: Throwable?
    ) : Error()
}