package com.vila.workmanager

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton

class MyCustomButtom : AppCompatButton ,View.OnClickListener {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setOnClickListener(this)
    }

    var count = 1
    var lastTimeClicked = 0L

    lateinit var listener : OnClickCustomListenerJava

    override fun onClick(view: View?) {
        if((System.currentTimeMillis()-lastTimeClicked)<=600){
            return

        }
        lastTimeClicked = System.currentTimeMillis()
        listener.onCustomClick ()
        /*Log.d("mcontrol","valor de count $count")
        count++*/



    }

    interface OnClickCustomListenerJava{
        fun onCustomClick()
    }

    interface OnClickCustomListenerKotlin{
        fun onCustomClick( function: () -> Unit)
    }

    fun setOnClickCustomListener(listener: OnClickCustomListenerJava){
        this.listener = listener
    }





}