package tech.coner.trailer.toolkit.sample.fooapp.presentation.presenter

import app.cash.turbine.test
import arrow.core.Either
import arrow.core.right
import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tech.coner.trailer.assertk.arrowkt.isLeft
import tech.coner.trailer.assertk.arrowkt.isRight
import tech.coner.trailer.toolkit.Error
import tech.coner.trailer.toolkit.presentation.model.*
import tech.coner.trailer.toolkit.presentation.model.isDirty
import tech.coner.trailer.toolkit.presentation.model.isValid
import tech.coner.trailer.toolkit.presentation.model.item
import tech.coner.trailer.toolkit.presentation.model.pendingItem
import tech.coner.trailer.toolkit.presentation.model.pendingItemValidation
import tech.coner.trailer.toolkit.presentation.presenter.PresenterCoroutineScope
import tech.coner.trailer.toolkit.presentation.state.loadable
import tech.coner.trailer.toolkit.presentation.state.state
import tech.coner.trailer.toolkit.presentation.state.value
import tech.coner.trailer.toolkit.sample.fooapp.data.repository.FooRepositoryImpl
import tech.coner.trailer.toolkit.sample.fooapp.data.service.FooServiceImpl
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.FOO_ID_FOO
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.repository.FooRepository
import tech.coner.trailer.toolkit.sample.fooapp.domain.service.FooService
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooValidator
import tech.coner.trailer.toolkit.sample.fooapp.presentation.adapter.FooEntityModelAdapter
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooItemModel
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.name
import tech.coner.trailer.toolkit.sample.fooapp.presentation.state.FooDetailState
import tech.coner.trailer.toolkit.validation.testsupport.isValid

class FooDetailPresenterTest {

    private val repository: FooRepository = FooRepositoryImpl()

    @Test
    fun itsModelFlowShouldBeAdaptedFromInitialState() = runTest {
        val id = Foo.Id(FOO_ID_FOO)
        val presenter = createPresenterInitial(id)

        presenter.stateFlow.test {
            assertThat(expectMostRecentItem())
                .loadable()
                .isEmpty()
        }
    }

    @Test
    fun itsModelFlowShouldEmitWhenLoadingAndLoaded() = runTest {
        val id = Foo.Id(FOO_ID_FOO)
        val presenter = createPresenterInitial(id)

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
                .item()
                .name()
                .length()
                .isEqualTo(1)
            assertThat(awaitItem())
                .loadable()
                .isLoaded()
                .either()
                .isRight()
                .item()
                .name()
                .isEqualTo("Foo")
        }
    }

    @Test
    fun itsModelFlowShouldEmitWhenLoadingAndLoadFailedNotFound() = runTest {
        val id = Foo.Id(Int.MAX_VALUE)
        val presenter = createPresenterInitial(id)

        presenter.stateFlow.test {
            skipItems(1)

            presenter.load()

            assertThat(awaitItem())
                .loadable()
                .isLoading()
            assertThat(awaitItem())
                .loadable()
                .isLoaded()
                .either()
                .isLeft()
                .isEqualTo(FooService.FindFailure.NotFound)
        }
    }

    @Test
    fun whenItsModelNameChangedValidItsItemModelShouldBeValid() = runTest {
        val foo = repository.read(Foo.Id(FOO_ID_FOO)).getOrThrow().getOrNull()!!
        val presenter = createPresenterLoaded(foo)

        presenter.name.value = "bax"
        presenter.validate()

        assertThat(presenter).all {
            name().value().isEqualTo("Bax")
            state()
                .loadable()
                .isLoaded()
                .either()
                .isRight()
                .all {
                    isValid().isTrue()
                    isDirty().isTrue()
                }
        }
    }

    @Test
    fun whenItsModelNameChangedInvalidItsItemModelShouldBeInvalid() = runTest {
        val foo = repository.read(Foo.Id(FOO_ID_FOO)).getOrThrow().getOrNull()!!
        val presenter = createPresenterLoaded(foo)

        presenter.name.value = "boo"
        presenter.validate()

        assertThat(presenter).all {
            name().value().isEqualTo("Boo")
            state()
                .loadable()
                .isLoaded()
                .either()
                .isRight()
                .all {
                    isValid().isFalse()
                    isDirty().isTrue()
                }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["Bar", "Baz", "Bat", "Cat", "Dat", "Far", "Ber", "Fir", "Nor", "Dur", "Xyr"])
    fun whenItsModelNameChangedValidItsItemModelShouldValidateValid(newName: String) = runTest {
        val foo = repository.read(Foo.Id(FOO_ID_FOO)).getOrThrow().getOrNull()!!
        val presenter = createPresenterLoaded(foo)

        presenter.name.value = newName
        presenter.validate()

        assertThat(presenter).all {
            state()
                .loadable()
                .isLoaded()
                .either()
                .isRight()
                .all {
                    pendingItem().name().isEqualTo(newName)
                    isValid().isTrue()
                    pendingItemValidation().isValid()
                    isDirty().isTrue()
                }
        }
    }
}

private fun TestScope.createPresenterInitial(id: Foo.Id): FooDetailPresenter {
    return createPresenter(
        argument = id,
    )
}

private fun TestScope.createPresenterLoaded(foo: Foo): FooDetailPresenter {
    val adapter = FooEntityModelAdapter()
    return createPresenter(
        initialState = FooDetailState(loadable = Loadable.Loaded(Either.Right(FooItemModel(adapter.entityToModelAdapter(foo))))),
        argument = foo.id,
        adapter = adapter,
    )
}

private fun TestScope.createPresenter(
    initialState: FooDetailState = FooDetailState(),
    argument: Foo.Id,
    adapter: FooEntityModelAdapter = FooEntityModelAdapter()
): FooDetailPresenter {
    return FooDetailPresenter(
        initialState = initialState,
        argument = argument,
        adapter = adapter,
        service = FooServiceImpl(
            repository = FooRepositoryImpl(),
            validator = FooValidator(),
        ),
        coroutineScope = PresenterCoroutineScope(coroutineContext + Job() + CoroutineName("FooDetailPresenter"))
    )
}

fun Assert<FooDetailPresenter>.name() = prop(FooDetailPresenter::name)
