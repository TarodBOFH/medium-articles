package org.tarodbofh.medium.junit5.parametrized

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParameterTests {

    private val service = Service()

    @ParameterizedTest
    @ArgumentsSource(ValidAmounts::class)
    fun `test service`(i: Int) {
        assertThat(service.doSomething(i)).isEqualTo(i)
    }

    @DisplayName("Error Cases")
    @Nested
    inner class ErrorCases {

        @ParameterizedTest
        @ValueSource(ints = [0, -100])
        fun `amount must be positive`(amount: Int) {
            assertThrows<MyBusinessException> {
                service.doSomething(amount)
            }
        }
    }
}

class Service {
    fun doSomething(i: Int) = i.takeIf { i > 0 } ?: throw MyBusinessException()
}

class MyBusinessException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)
