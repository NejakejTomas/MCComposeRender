package cz.nejakejtomas.mccomposerender

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.internal.MainDispatcherFactory

@OptIn(InternalCoroutinesApi::class)
class CustomMainDispatcherFactory : MainDispatcherFactory {
    override val loadPriority = Int.MAX_VALUE

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
        return MainDispatcher
    }
}