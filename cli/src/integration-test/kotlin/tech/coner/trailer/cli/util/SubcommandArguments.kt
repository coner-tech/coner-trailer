package tech.coner.trailer.cli.util

data class SubcommandArguments(
    val args: List<String>
) {
    constructor(vararg args: String) : this(args.toList())
}
