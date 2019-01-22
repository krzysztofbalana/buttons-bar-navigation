package com.krzysztofbalana.buttonsbar.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.krzysztofbalana.buttonsbar.utils.FontScalingStrategy
import com.krzysztofbalana.buttonsbar.utils.StrategyExecutor

class ButtonBarItemsHolder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    var strategyExecutor: StrategyExecutor = StrategyExecutor.Builder()
        .addStrategy(FontScalingStrategy(resources.displayMetrics.scaledDensity, 10F))
        .build()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)

        val (totalWidth, totalHeight) = recalculateChildren(desiredWidth, widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(totalWidth, totalHeight)
    }

    private fun recalculateChildren(desiredWith: Int, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
        val (totalWidth, totalHeight) = measureChildrensActualDimensions(widthMeasureSpec, heightMeasureSpec)

        if (desiredWith < totalWidth) {
            strategyExecutor.executeStrategyOn(viewChildren()) {
                recalculateChildren(desiredWith, widthMeasureSpec, heightMeasureSpec)
            }
        }
        Log.i("ButtonBarItemsHolder", "Desired width: $desiredWith, childrensWidth: $totalWidth")
        return Pair(totalWidth, totalHeight)
    }

    private fun measureChildrensActualDimensions(widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
        var totalWidth = 0
        var totalHeight = 0

        viewChildren<ButtonBarItem>().forEach {
            measureChild(it, widthMeasureSpec, heightMeasureSpec)
            totalWidth += it.measuredWidth
            if (it.measuredHeight > totalHeight) {
                totalHeight = it.measuredHeight
            }
        }
        return Pair(totalWidth, totalHeight)
    }
}

fun <T> ViewGroup.viewChildren(): List<T> = (0 until this.childCount).map { this.getChildAt(it) as T }
