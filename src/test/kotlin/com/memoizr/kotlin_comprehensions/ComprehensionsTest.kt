package com.memoizr.kotlin_comprehensions

import com.memoizr.assertk.expect
import org.junit.Test

class ComprehensionsTest {
    @Test
    fun hey() {
        val sequence = from(
                listOf(1, 2),
                { listOf("a", "b", "c") }
        ) where { a, b -> Pair(a, b) != Pair(2, "b") } yield { a, b -> Pair(a, b) }

        expect that sequence.toList() isEqualTo listOf(Pair(1, "a"), Pair(1, "b"), Pair(1, "c"), Pair(2, "a"), Pair(2, "c"))
    }
}