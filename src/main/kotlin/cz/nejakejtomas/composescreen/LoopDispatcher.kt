package cz.nejakejtomas.composescreen

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

internal abstract class LoopDispatcher : CoroutineDispatcher() {
    private var queue = mutableListOf<Runnable>()
    private var buffer = mutableListOf<Runnable>()

    override fun dispatch(context: CoroutineContext, block: Runnable): Unit = synchronized(queue) {
        queue.add(block)
    }

    protected fun run() {
        val q: MutableList<Runnable>

        synchronized(queue) {
            synchronized(buffer) {
                q = queue
                queue = buffer
                buffer = q
            }

            q.forEach { it.run() }
            q.clear()
        }
    }
}