package tech.coner.trailer.presentation.presenter

interface Presenter<ARGUMENT : Presenter.Argument> {

    interface Argument {
        object Nothing : Argument {

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                return true
            }

            override fun hashCode(): Int {
                return javaClass.hashCode()
            }
        }
    }


}
