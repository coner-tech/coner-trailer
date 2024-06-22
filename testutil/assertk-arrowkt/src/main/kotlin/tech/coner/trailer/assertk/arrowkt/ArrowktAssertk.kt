package tech.coner.trailer.assertk.arrowkt

import arrow.core.Either
import arrow.core.getOrElse
import assertk.Assert
import assertk.assertions.support.expected

/**
 * Asserts the Either value is Either.Right. You can pass in an optional lambda to run additional assertions on the right value
 *
 * ```
 * val name = Either.Right("...")
 * assertThat(name).isRight().hasLength(3)
 * ```
 */
fun <B : Any> Assert<Either<*, B>>.isRight(): Assert<B> = transform { actual ->
    actual.getOrElse { expected("to be right") }
}

/**
 * Asserts the Either value is Either.Left. You can pass in an optional lambda to run additional assertions on the left value
 *
 * ```
 * val name = Either.Left("...")
 * assertThat(name).isLeft().hasLength(3)
 * ```
 */
fun <A : Any> Assert<Either<A, *>>.isLeft(): Assert<A> = transform { actual ->
    actual.swap().getOrElse { expected("to be left") }
}