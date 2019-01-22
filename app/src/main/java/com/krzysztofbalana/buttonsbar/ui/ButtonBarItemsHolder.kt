package com.krzysztofbalana.buttonsbar.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class ButtonBarItemsHolder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)

        val (totalWidth, totalHeight) = recalculateChildren(desiredWidth, widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(totalWidth, totalHeight)
}

    private fun recalculateChildren(desiredWith: Int, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
        val (totalWidth, totalHeight) = measureChildrensActualDimensions(widthMeasureSpec, heightMeasureSpec)

    }
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