package com.nextshop.mechanism

import kotlinx.coroutines.*

object SimpleCoroutines {

    fun <T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@ {
                return@rt work()
            }.await()
            callback(data)
        }
}