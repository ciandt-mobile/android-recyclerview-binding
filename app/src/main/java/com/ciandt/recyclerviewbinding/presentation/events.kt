package com.ciandt.recyclerviewbinding.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

open class Event<out T>(val data: T) {

    var hasBeenHandled = false
        protected set

    fun subscribe(body: Event<T>.() -> Unit) {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            body(this)
        }
    }
}

class SimpleEvent : Event<Any?>(null)

fun <T : Event<*>> LiveData<T>.subscribe(owner: LifecycleOwner, body: T.() -> Unit) {
    observe(owner, Observer {
        it?.subscribe { body(it) }
    })
}