package tech.coner.trailer.toolkit.sample.dmvapp.gui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import tech.coner.trailer.toolkit.presentation.state.ItemModelState
import tech.coner.trailer.toolkit.presentation.state.MutableItemModelPropertyDelegate
import tech.coner.trailer.toolkit.presentation.state.State
import tech.coner.trailer.toolkit.presentation.state.StateContainer
import tech.coner.trailer.toolkit.validation.Feedback

@Composable
fun <STATE : State, PROPERTY> StateContainer<STATE>.StatePropertyContainer<PROPERTY>.collectAsState(
    context: CoroutineContext = EmptyCoroutineContext
) = flow.collectAsState(immutableValue, context)

@Composable
fun <STATE : ItemModelState<ITEM, FEEDBACK>, ITEM, PROPERTY, FEEDBACK : Feedback<ITEM>>
        MutableItemModelPropertyDelegate<STATE, ITEM, FEEDBACK, PROPERTY>.collectValueAsState(
    context: CoroutineContext = EmptyCoroutineContext
) = valueFlow.collectAsState(
    initial = value,
    context = context
)

@Composable
fun <STATE : ItemModelState<ITEM, FEEDBACK>, ITEM, PROPERTY, FEEDBACK : Feedback<ITEM>>
MutableItemModelPropertyDelegate<STATE, ITEM, FEEDBACK, PROPERTY>.collectFeedbackAsState(
    context: CoroutineContext = EmptyCoroutineContext
) = feedbackFlow.collectAsState(
    initial = feedback,
    context = context
)
