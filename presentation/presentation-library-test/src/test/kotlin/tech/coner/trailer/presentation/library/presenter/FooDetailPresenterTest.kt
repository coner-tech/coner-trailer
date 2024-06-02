package tech.coner.trailer.presentation.library.presenter

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.length
import assertk.assertions.prop
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import tech.coner.trailer.presentation.library.model.cause
import tech.coner.trailer.presentation.library.model.isEmpty
import tech.coner.trailer.presentation.library.model.isLoadFailed
import tech.coner.trailer.presentation.library.model.isLoaded
import tech.coner.trailer.presentation.library.model.isLoading
import tech.coner.trailer.presentation.library.model.item
import tech.coner.trailer.presentation.library.model.partial
import tech.coner.trailer.presentation.library.state.loadable
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.FOO_ID_FOO
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.exception.NotFoundException
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.service.TestableFooService
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.presenter.FooDetailPresenter

class FooDetailPresenterTest {

    @Test
    fun itsModelFlowShouldBeAdaptedFromInitialState() = runTest {
        val id = Foo.Id(FOO_ID_FOO)
        val presenter = createPresenter(id)

        presenter.stateFlow.test {
            assertThat(expectMostRecentItem())
                .loadable()
                .isEmpty()
        }
    }

    @Test
    fun itsModelFlowShouldEmitWhenLoadingAndLoaded() = runTest {
        val id = Foo.Id(FOO_ID_FOO)
        val presenter = createPresenter(id)

        presenter.stateFlow.test {
            skipItems(1)

            presenter.load()

            assertThat(awaitItem())
                .loadable()
                .isLoading()
                .partial()
                .isNull()
            assertThat(awaitItem())
                .loadable()
                .isLoading()
                .partial()
                .isNotNull()
                .prop(FooModel::original)
                .prop(Foo::name)
                .length()
                .isEqualTo(1)
            assertThat(awaitItem())
                .loadable()
                .isLoaded()
                .item()
                .prop(FooModel::original)
                .prop(Foo::name)
                .isEqualTo("foo")
        }
    }

    @Test
    fun itsModelFlowShouldEmitWhenLoadingAndLoadFailed() = runTest {
        val id = Foo.Id(Int.MAX_VALUE)
        val presenter = createPresenter(id)

        presenter.stateFlow.test {
            skipItems(1)

            presenter.load()

            assertThat(awaitItem())
                .loadable()
                .isLoading()
            assertThat(awaitItem())
                .loadable()
                .isLoadFailed()
                .cause()
                .isNotNull()
                .isInstanceOf<NotFoundException>()
        }
    }
}

private fun TestScope.createPresenter(argument: Foo.Id): FooDetailPresenter {
    return FooDetailPresenter(
        argument = argument,
        service = TestableFooService(
            constraint = FooConstraint()
        ),
        coroutineContext = coroutineContext + Job() + CoroutineName("FooDetailPresenter")
    )
}
