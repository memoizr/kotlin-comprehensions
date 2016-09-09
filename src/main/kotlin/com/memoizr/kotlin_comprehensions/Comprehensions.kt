package com.memoizr.kotlin_comprehensions

class FromBuilder1<A : Any, AI : Iterable<A>>(val a: AI) {
    var where: (A) -> Boolean = { a -> true }
    infix fun where(op: (A) -> Boolean): FromBuilder1<A, AI> {
        where = op
        return this
    }

    infix fun <T> yield(op: (A) -> T): Sequence<T> {
        val asSequence = a.asSequence()
        return asSequence.filter { where(it) }.map { op(it) }
    }

    operator infix fun <T> invoke(op: (A) -> T) = yield(op)
}

class FromBuilder<A : Any, AI : Iterable<A>, B : Any, BI : Iterable<B>>(
        val a: AI,
        val b: (A) -> BI
) {
    var where: (A, B) -> Boolean = { a, b -> true }
    infix fun where(op: (A, B) -> Boolean): FromBuilder<A, AI, B, BI> {
        where = op
        return this
    }

    infix fun <T> yield(op: (A, B) -> T): Sequence<T> {

        val asSequence = a.asSequence()
        return asSequence.flatMap { ap ->
            b(ap).asSequence().filter { where(ap, it) }.map { op(ap, it) }
        }
    }
}

fun <A : Any, AI : Iterable<A>, B : Any, BI : Iterable<B>> from(
        a: AI,
        b: (A) -> BI
) = FromBuilder(a, b)

fun <A : Any, AI : Iterable<A>> from(
        a: AI
) = FromBuilder1(a)
