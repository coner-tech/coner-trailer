package tech.coner.trailer.toolkit.sample.fooapp.domain.validation

import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback.IdMustBeInRange
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback.NameMustBeLowercaseLettersOnly
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback.NameMustBeThreeCharacters
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback.NameOtherThanFooMustFollowPatternOfConsonantVowelConsonant
import tech.coner.trailer.toolkit.validation.Validator

typealias FooValidator = Validator<Unit, Foo, FooFeedback>

fun FooValidator() = Validator<Unit, Foo, FooFeedback> {
    Foo::id { id -> IdMustBeInRange.takeUnless { id.value in 0..4 } }
    Foo::name { name -> NameMustBeLowercaseLettersOnly.takeUnless { name.all { it.isLowerCase() } } }
    Foo::name { name -> NameMustBeThreeCharacters.takeUnless { name.length == 3 } }

    val vowels = "aeiouy"
    val consonants = "bcdfghjklmnpqrstvwxz"
    val namesOtherThanFooPattern = Regex("[$consonants][$vowels][$consonants]")
    Foo::name { name ->
        NameOtherThanFooMustFollowPatternOfConsonantVowelConsonant.takeUnless {
            when (name) {
                "foo" -> true
                else -> namesOtherThanFooPattern.matches(name)
            }
        }
    }
}