package presenter

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter
import tech.coner.trailer.presentation.library.state.LoadableItem
import tech.coner.trailer.presentation.library.testsupport.itemModel

class LoadableItemPresenterTest {

    private val factory: (TestableLoadableItemPresenter.State) -> TestableLoadableItemPresenter = {
        TestableLoadableItemPresenter(
            initialState = it,
            adapter = LoadableItemAdapter(
                itemAdapter = TestableLoadableItemPresenter.ItemAdapter()
            )
        )
    }

    @Test
    fun itsItemModelShouldBeAdaptedFromInitialState() {
        val loadable = LoadableItem.Empty<TestableLoadableItemPresenter.Item>()
        val state = TestableLoadableItemPresenter.State(loadable)

        val presenter = factory(state)

        val expected = TestableLoadableItemPresenter.Model(
            original = TestableLoadableItemPresenter.Item(0),
            
        )
        assertThat(presenter).itemModel().isEqualTo(expected)
    }
}