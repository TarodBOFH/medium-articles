package org.tarodbofh.medium.junit5.parametrized

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

inline fun <reified T : Any> arguments(vararg args: T): ArgumentsProvider = ArgumentsProvider {
    Stream.of(*args).map { Arguments.of(it) }
}

fun IntRange.toTypedArray() = this.toList().toTypedArray()

val valid_amounts = (1..10)

internal class ValidAmounts : ArgumentsProvider by arguments(*valid_amounts.toTypedArray())

