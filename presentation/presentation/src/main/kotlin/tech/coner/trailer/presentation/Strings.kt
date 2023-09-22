package tech.coner.trailer.presentation

object Strings {
    const val abbreviationDisqualified = "DSQ"
    const val abbreviationDidNotFinish = "DNF"
    const val abbreviationRerun = "RRN"

    // Constraints
    const val constraintsEventRunLatestCountMustBeGreaterThanZero = "Count must be greater than or equal to 1"

    // Error messages
    const val errorUnknownMessage = "Something went wrong"

    const val errorNoDatabaseChosenTitle = "No database chosen"
    const val errorNoDatabaseChosenMessage = "No database argument was given and/or no default database was configured, and invoked subcommand requires one. See: coner-trailer-cli config database"

    const val errorRecordNotFoundTitle = "Record not found"

    const val errorReadTitle = "Failed to read a record from storage"
    const val errorReadMessageSuffix = "The record might be from a different version of software, or it might be corrupt."

    const val errorValidationTitle = "Validation Failed"
}