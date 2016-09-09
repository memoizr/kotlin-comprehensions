package com.memoizr.kotlin_comprehensions

val <T> T?.asIterable: Iterable<T> get() = this?.let { Iterable { sequenceOf(this).iterator() } } ?: Iterable { emptyList<T>().iterator() }

