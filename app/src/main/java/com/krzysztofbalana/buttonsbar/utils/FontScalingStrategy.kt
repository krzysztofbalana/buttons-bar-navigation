package com.krzysztofbalana.buttonsbar.utils

import android.util.TypedValue
import com.krzysztofbalana.buttonsbar.ui.ButtonBarItem
import java.math.RoundingMode
import java.text.DecimalFormat

class FontScalingStrategy(private val scaleDensity: Float, private val minFontSize: Float) : Strategy<ButtonBarItem>() {

    private val decimalFormatter: DecimalFormat
        get() {
            val decimalFormat = DecimalFormat("#")
            decimalFormat.roundingMode = RoundingMode.CEILING
            return decimalFormat
        }

    companion object {
        const val FONT_SCALE_STEP = 1f
    }

    override fun execute(view: ButtonBarItem, onApplied: () -> Unit, onDepleted: () -> Unit) {
        require(scaleDensity != 0F, { "Scale density cannot be equal to 0" })

        val currentFontSize: Float = view.title.textSize / scaleDensity
        val currentRoundedFontSize = decimalFormatter.format(currentFontSize).toFloat()


        if (currentRoundedFontSize == minFontSize) {
            onDepleted()
        }
        if (currentRoundedFontSize > minFontSize) {
            view.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentFontSize - FONT_SCALE_STEP)
            onApplied()
        }
    }
}