package com.vila.workmanager

import android.util.Log
import android.view.View




fun View.setOnDoubleClickListener(action :()->Unit){
    val time = 600L
    var lastTimeClicked = 0L

    this.setOnClickListener {
        if ((System.currentTimeMillis()-lastTimeClicked)<=time){
            Log.d("mcontrol","clicked repeated")

            return@setOnClickListener
        }
        lastTimeClicked = System.currentTimeMillis()
        action()
    }

}


fun View.setOnDoubleClickListenerCustom( listener:DoubleCLickLstner){
    val time = 600L
    var lastTimeClicked = 0L
    this.setOnClickListener {
        if ((System.currentTimeMillis() - lastTimeClicked) <= time) {
            Log.d("mcontrol", "clicked repeated")

            return@setOnClickListener
        }
        lastTimeClicked = System.currentTimeMillis()
        listener.customClick()
    }
}

interface DoubleCLickLstner{
    fun customClick()
}
