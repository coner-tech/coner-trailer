package org.coner.trailer.cli.command

/**
 * Any subcommands that make authenticated requests to MotorsportReg API must implement this interface.
 *
 * The root command will determine if any invoked subcommands implement this and abort if the options are not present.
 */
interface RequiresMotorsportRegAuthentication