package org.coner.trailer.cli.view

interface View<M> {

    fun render(model: M): String
}