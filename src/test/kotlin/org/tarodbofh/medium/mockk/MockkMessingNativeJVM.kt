package org.tarodbofh.medium.mockk

import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class MockkMessingNativeJVM {

    @Test
    fun testConstructorGetParametersMocked() {
        var parameters = DataClass::class.java.constructors[0].parameters

        assert(parameters.map { it.name }.contains("name"))
        assert(parameters.map { it.name }.contains("age"))

        // inject a mockk object to alter native constructor.getParameters
        val instance: DataClass = mockk()

        clearMocks()
        unmockkAll()

        parameters = DataClass::class.java.constructors[0].parameters

        assert(parameters.map { it.name }.contains("name"))
        assert(parameters.map { it.name }.contains("age"))
    }

    @Test
    fun testConstructorEqualityAfterClearMocks() {
        val javaClass = DataClass::class.java

        val constructor = DataClass::class.java.constructors[0]

        assert(javaClass.constructors.size == 1)

        // inject a mockk object to alter native constructor.getParameters
        val instance: DataClass = mockk()

        clearMocks()
        unmockkAll()

        assert(javaClass === DataClass::class.java)
        assert(constructor.parameterCount == DataClass::class.java.constructors[0].parameterCount)
        assert(constructor.parameters.map { it.name } == DataClass::class.java.constructors[0].parameters.map { it.name })
        assert(constructor == DataClass::class.java.constructors[0])
    }
}

data class DataClass(val name: String, val age: Int)
data class DataClassForMockito(val name: String, val age: Int)
class ServiceForMockito {
    fun getData(name: String, age: Int) = DataClassForMockito(name, age)
}
