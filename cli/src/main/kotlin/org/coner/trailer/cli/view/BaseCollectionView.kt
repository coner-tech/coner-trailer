package org.coner.trailer.cli.view

import com.github.ajalt.clikt.output.CliktConsole

abstract class BaseCollectionView<M> : View<M> {
    abstract val console: CliktConsole

    open fun render(models: Collection<M>) = models.joinToString(
            separator = console.lineSeparator
    ) { render(it) }
}