package tech.coner.trailer.presentation.library.presenter

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.presenter.FooPresenter

class LoadableItemPresenterTest {


    @Test
    fun itsModelFlowShouldBeAdaptedFromInitialState() = runTest {
        val presenter = FooPresenter()

        val actual = presenter.modelFlow.first()

        assertThat(actual).isEqualTo(LoadableModel.Empty(null))
    }

    @Test
    fun itsModelFlowShouldChangeToLoadingWhenLoadingBegins() = runTest {
        val presenter = FooPresenter()
    }

    @Test
    fun itsModelFlowShouldChangeToLoadedWhenLoadingFinishes() = runTest {
        TODO()
    }
}