package com.krzysztofbalana.buttonsbar.utils

import com.krzysztofbalana.buttonsbar.ui.ButtonBarItem


class StrategyExecutor(private var strategies: List<Strategy<ButtonBarItem>>) {

    private var counter: Int = 0

    private constructor(builder: Builder) : this(builder.strategies)

    fun executeStrategyOn(items: List<ButtonBarItem>, onStrategyExecuted: () -> Unit) {
        resetCounter()

        val strategy = strategies.firstOrNull()

        items.forEachIndexed { index, view ->
            strategy?.execute(view,
                {  counter++ },
                { }
            )
        }

        if (counter == items.size) {
            onStrategyExecuted.invoke()
        }
    }

    private fun resetCounter() {
        counter = 0
    }

    class Builder {

        var strategies: MutableList<Strategy<ButtonBarItem>> = mutableListOf()

        fun addStrategy(strategy: Strategy<ButtonBarItem>): Builder {
            this.strategies.add(strategy)
            return this
        }

        fun build(): StrategyExecutor {
            return StrategyExecutor(this)
        }

    }

}