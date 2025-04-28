package cz.nejakejtomas.mccomposerender

import com.mojang.blaze3d.systems.RenderSystem
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

internal object MainDispatcher : MainCoroutineDispatcher() {
    private var queue = mutableListOf<Runnable>()
    private var buffer = mutableListOf<Runnable>()

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (RenderSystem.isOnRenderThread()) block.run()
        else {
            synchronized(queue) {
                queue.add(block)
            }
        }
    }

    internal fun run() {
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

    override val immediate: MainCoroutineDispatcher
        get() = this
}