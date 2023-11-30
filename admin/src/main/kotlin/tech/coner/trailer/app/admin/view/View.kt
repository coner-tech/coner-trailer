package tech.coner.trailer.app.admin.view

interface View<M> {

    fun render(model: M): String
}