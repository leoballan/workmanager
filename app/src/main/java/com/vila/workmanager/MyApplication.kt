package com.vila.workmanager

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf

class MyApplication : Application() {

  //  lateinit var workManager : WorkManager

    override fun onCreate(){
        super.onCreate()
    //    workManager = WorkManager.getInstance(this)
    }

   /* fun configWorker(data:String){

        val mData = workDataOf(MainActivity.FALLO to data)

        val workRequest = OneTimeWorkRequest.Builder(MyCoroutineWork::class.java)
            .setInputData(mData)
            .build()

        workManager.enqueueUniqueWork(MyCoroutineWork::class.java.simpleName
            , ExistingWorkPolicy.REPLACE
            ,workRequest)
    }

    fun stopworker(){

    }*/
}