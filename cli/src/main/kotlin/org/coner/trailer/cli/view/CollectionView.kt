package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole

interface CollectionView<M> : View<M> {
    val console: CliktConsole

    open fun render(models: Collection<M>) = models.joinToString(
            separator = console.lineSeparator
    ) { render(it) }
}