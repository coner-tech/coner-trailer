package tech.coner.trailer.app.admin.view

import com.github.ajalt.mordant.terminal.Terminal

abstract class BaseCollectionView<M> : View<M> {
    abstract val terminal: Terminal

    open fun render(models: Collection<M>) = models.joinToString(
            separator = System.lineSeparator()
    ) { render(it) }
}