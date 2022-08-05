package com.vila.workmanager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton

class MyButton(context: Context, attributes: AttributeSet? = null) :
    AppCompatButton(context, attributes) {

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        Log.d("mcontrol","inside click listener")
    }
}