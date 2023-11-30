package tech.coner.trailer.app.admin.command

/**
 * Direct subcommands may implement this to bypass the requirement to choose a database.
 *
 * This comes into play when no database exists yet (during a first-run, etc), or no
 * default is available and no choice was made.
 */
interface PermitNoDatabaseChosen