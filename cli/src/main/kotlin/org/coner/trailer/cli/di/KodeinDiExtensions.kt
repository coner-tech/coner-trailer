package org.coner.trailer.cli.di

import org.kodein.di.DIContext
import org.kodein.di.bindings.ScopeCloseable

/**
 * Execute `fn` and then close the DI scope context.
 *
 * The scope context will be closed even if `fn` throws.
 *
 * @param fn the function to execute
 */
fun <SC : ScopeCloseable, R> DIContext<SC>.use(fn: () -> R): R {
    try {
        return fn()
    } finally {
        value.close()
    }
}
