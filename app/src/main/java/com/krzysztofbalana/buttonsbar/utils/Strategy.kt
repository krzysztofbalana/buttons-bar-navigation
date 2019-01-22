package com.krzysztofbalana.buttonsbar.utils

abstract class Strategy<V> {

    abstract fun execute(view: V, onApplied: () -> Unit, onDepleted: () -> Unit)
}