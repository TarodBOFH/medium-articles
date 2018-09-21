package org.tarodbofh.medium.collections

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LambdaFilterTests {

    @Test
    fun `filter then map`() {
        val listData = mutableListOf<TestData>()
        val strings = mutableListOf<String>()

        for(i in 1..1000) {
            listData.add(TestData(name = "Name", age = i))
            strings.add("${i}_NameXXX")
        }

        var timesMap = 0
        var timesFilter = 0

        val filtered = strings.filter {
            timesFilter++
            listData.map {
            timesMap++
            it.name
            }.toSet()
                .contains(
                    it.split("_").last().substring { it.substring(0, it.length-3) })
        }


        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $timesFilter \n\t" +
            "Map        : $timesMap")

    }


    @Test
    fun `map then filter`() {
        val listData = mutableListOf<TestData>()
        val strings = mutableListOf<String>()

        for(i in 1..1000) {
            listData.add(TestData(name = "Name", age = i))
            strings.add("${i}_NameXXX")
        }

        var timesMap = 0
        var timesFilter = 0

        var stringData = listData.map {
            timesMap++
            it.name
        }.toSet()

        val filtered = strings.filter {
            timesFilter++
            stringData
                .contains(
                    it.split("_").last().substring { it.substring(0, it.length-3) })
        }

        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $timesFilter \n\t" +
            "Map        : $timesMap")

    }

    @Test
    fun `even_simplier_filter_with_map`() {
        val listData = mutableListOf<TestData>()
        val strings = mutableListOf<String>()

        for(i in 1..1000) {
            listData.add(TestData(name = "Name", age = i))
            strings.add("${i}_NameXXX")
        }

        var timesMap = 0
        var timesFilter = 0

        val filtered = strings.filter {
            timesFilter++
            it.endsWithAny( listData.map {
                timesMap++
                it.name
            }.toSet())
        }

        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $timesFilter \n\t" +
            "Map        : $timesMap")
    }

    @Test
    fun `even_simplier`() {
        val listData = mutableListOf<TestData>()
        val strings = mutableListOf<String>()

        for(i in 1..1000) {
            listData.add(TestData(name = "Name", age = i))
            strings.add("${i}_NameXXX")
        }

        var timesMap = 0
        var timesFilter = 0

        val stringData = listData.map {
            timesMap++
            it.name
        }.toSet()

        val filtered = strings.filter {
            timesFilter++
            it.endsWithAny(stringData)
        }

        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $timesFilter \n\t" +
            "Map        : $timesMap")
    }


    @Test
    fun `even_simplier_as_sequence`() {
        val listData = mutableListOf<TestData>()
        val strings = mutableListOf<String>()

        for(i in 1..1000) {
            listData.add(TestData(name = "Name", age = i))
            strings.add("${i}_NameXXX")
        }

        var timesMap = 0
        var timesFilter = 0

        val stringData = listData.asSequence().map {
            timesMap++
            it.name
        }.toSet()

        val filtered = strings.filter {
            timesFilter++
            it.endsWithAny(stringData)
        }

        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $timesFilter \n\t" +
            "Map        : $timesMap")
    }

    private fun createTestData(i: Int) = (1..i).map {TestData(name = "Name", age = it) }

    fun createTestStrings(i: Int) = (1..i).map { "${it}_NameXXX" }

    var staticTimesMap = 0
    var staticTimesFilter = 0

    private fun oneLiner(testData: List<TestData>) = testData.map {
        staticTimesMap++
        it.name
    }.run {
        createTestStrings(1000).filter {
            staticTimesFilter++
            it.endsWithAny(this )
        }
    }

    @Test
    fun `test one liner`() {
        val testData = createTestData(1000)
        val filtered = oneLiner(testData)

        println("" +
            "Result Size: ${filtered.size}\n" +
            "Invocations\n\t" +
            "Filter     : $staticTimesFilter \n\t" +
            "Map        : $staticTimesMap")
    }

    @Test
    fun `test_substr_as_lambda`() {
        val str = "thisis_astring1"
        assertThat(str.split("_").last().substring {
            it.substring(0,it.length-1)
        }).isEqualTo("astring")
    }
}

private data class TestData(val name: String, val age: Int)

private fun String?.substring(block: (String) -> String) = this?.let {block.invoke(this)}

private fun String?.endsWithAny(vararg s: String): Boolean = this?.let { _ ->
    s.firstOrNull {
        this.endsWith(it)
    }
} != null ?: false

private fun String?.endsWithAny(s: Collection<String>) = this?.let { _ ->
    s.firstOrNull {
        this.endsWith(it)
    }
} != null ?: false