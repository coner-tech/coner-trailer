package org.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.io.service.PolicyService

fun CliktCommand.policySelectOptionGroup(
    serviceFn: () -> PolicyService
) = mutuallyExclusiveOptions(
    option("--policy-id")
        .convert { toUuid(it) }
        .convert { serviceFn().findById(it) },
    option("--policy-named")
        .convert { serviceFn().findByName(it) }
)