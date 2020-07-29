package org.coner.trailer.cli.di

import org.kodein.di.DI

val di = DI {
    import(cliktModule)
    import(ioModule)
}

