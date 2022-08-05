package com.vila.workmanager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCoroutineWorkWithHandler (context: Context, parameter: WorkerParameters):
    CoroutineWorker (context,parameter){

    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable  {

            Log.d("mControl","------ dentro del handler ----")
            runAgain()

    }


    override suspend fun doWork(): Result {
        return withContext(Dispatchers.Default){

            handler.post(runnable)
            Log.d("mControl","Se termino LA ejecucion del trabajo UploadWork")
            Result.success()
        }
    }

    private fun runAgain(){
        handler.postDelayed(runnable,5000)
    }

}