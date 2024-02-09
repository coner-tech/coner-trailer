package presenter

import tech.coner.trailer.presentation.library.adapter.LoadableItemAdapter

class LoadableItemPresenterTest {

    private val factory: (TestableLoadableItemPresenter.State) -> TestableLoadableItemPresenter = {
        TestableLoadableItemPresenter(
            initialState = it,
            adapter = LoadableItemAdapter(
                itemAdapter = TestableLoadableItemPresenter.ItemAdapter()
            )
        )
    }

}