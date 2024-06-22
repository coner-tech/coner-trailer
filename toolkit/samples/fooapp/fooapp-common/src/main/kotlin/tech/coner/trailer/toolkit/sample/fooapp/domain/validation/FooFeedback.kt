package tech.coner.trailer.toolkit.sample.fooapp.domain.validation

import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.Severity
import kotlin.reflect.KProperty1

sealed class FooFeedback : Feedback<Foo> {

    override val severity = Severity.Error

    data object IdMustBeInRange : FooFeedback() {
        override val property = Foo::id
    }
    data object NameMustBeLowercaseLettersOnly : FooFeedback() {
        override val property = Foo::name
    }
    data object NameMustBeThreeCharacters : FooFeedback() {
        override val property = Foo::name
    }
    data object NameOtherThanFooMustFollowPatternOfConsonantVowelConsonant : FooFeedback() {
        override val property = Foo::name
    }
}