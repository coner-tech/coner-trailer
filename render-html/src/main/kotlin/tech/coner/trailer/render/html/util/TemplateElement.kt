package tech.coner.trailer.render.html.util

import kotlinx.html.*

class TEMPLATE(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "template",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = false
), HtmlBlockTag

fun HtmlBlockTag.template(block: TEMPLATE.() -> Unit = {}) {
    return TEMPLATE(consumer).visit(block)
}

fun PhrasingContent.template(block: TEMPLATE.() -> Unit = {}) {
    return TEMPLATE(consumer).visit(block)
}