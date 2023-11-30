package tech.coner.trailer.app.admin.util

data class SubcommandArguments(
    val args: List<String>
) {
    constructor(vararg varargs: String) : this(varargs.toList())

    companion object {
        operator fun invoke(builderFn: (MutableList<String>).() -> Unit) =
            SubcommandArguments(args = mutableListOf<String>().apply(builderFn))
    }
}
