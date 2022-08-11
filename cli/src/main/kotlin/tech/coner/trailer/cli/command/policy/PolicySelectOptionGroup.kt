package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.PolicyService

fun BaseCommand.policySelectOptionGroup(
    serviceFn: () -> PolicyService
) = mutuallyExclusiveOptions(
    option("--policy-id")
        .convert { toUuid(it) }
        .convert { serviceFn().findById(it) },
    option("--policy-named")
        .convert { serviceFn().findByName(it) }
)