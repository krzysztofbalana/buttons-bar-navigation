package com.krzysztofbalana.buttonsbar.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.krzysztofbalana.buttonsbar.R

class ButtonBarItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @BindView(R.id.border)
    lateinit var border: View

    @BindView(R.id.button_bar_item_icon)
    lateinit var icon: ImageView

    @BindView(R.id.button_bar_item_title)
    lateinit var title: TextView


    private val mainView: View

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mainView = inflater.inflate(R.layout.view_button_bar_item, this)

        ButterKnife.bind(mainView, this)

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ButtonBar, 0, 0)

        try {
            title.text = typedArray.getText(R.styleable.ButtonBar_buttonText)
            val iconDrawable =
                ContextCompat.getDrawable(context, typedArray.getResourceId(R.styleable.ButtonBar_buttonIcon, 0))
            icon.setImageDrawable(iconDrawable)

        } finally {
            typedArray.recycle()
        }
    }

}
