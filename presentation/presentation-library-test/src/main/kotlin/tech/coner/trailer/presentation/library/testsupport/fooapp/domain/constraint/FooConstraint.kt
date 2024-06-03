package tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint

import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.konstraints.CompositeConstraint

class FooConstraint : CompositeConstraint<Foo>() {

    val valueIsInRange = propertyConstraint(
        property = Foo::id,
        assessFn = {
            when (it.id.value) {
                in 0..4 -> true
                else -> false
            }
        },
        violationMessageFn = { "ID value must be in 0 to 4" }
    )


    val nameIsLettersOnly = propertyConstraint(
        property = Foo::name,
        assessFn = { foo -> foo.name.all { it.isLetter() } },
        violationMessageFn = { "Name must be only letters" }
    )

    val nameMustBeThreeCharacters = propertyConstraint(
        property = Foo::name,
        assessFn = { foo -> foo.name.length == 3 },
        violationMessageFn = { "Name must be three letters long" }
    )

    private val vowels = "aeiouy"
    private val consonants = "bcdfghjklmnpqrstvwxz"
    private val namesOtherThanFooPattern = Regex("[$consonants][$vowels][$consonants]")

    val namesOtherThanFooMustFollowPatternOfConsonantVowelConsonant = propertyConstraint(
        property = Foo::name,
        assessFn = { foo ->
            when (foo.name) {
                "foo" -> true
                else -> namesOtherThanFooPattern.matches(foo.name)
            }
        },
        violationMessageFn = { "Names other than foo must start with a consonant, followed by a vowel, and end with a consonant" }
    )
}