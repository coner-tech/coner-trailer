package org.coner.trailer.cli.di

import org.kodein.di.DI

val trailerCliGlobalDi = DI {
    import(viewModule)
    import(cliktModule)
    import(ioModule)
}

