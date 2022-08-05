package com.vila.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.vila.workmanager.MainActivity.Companion.FALLO
import kotlinx.coroutines.*

class MyCoroutineWork
    (context:Context ,params: WorkerParameters)
    :CoroutineWorker(context,params) {


    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.Default){
                Log.d("webservice","dentro  de coroutine worker -------")
                while (isActive){
                    longFunction()
                    yield()
                    delay(60000)
                }

                    //Result.failure()
            }
        } catch (e: Exception) {
            Log.d("webservice","Error dentro de la coroutina ${e.message}.....$e")
            return Result.failure()
        }
        return Result.success(workDataOf(FALLO to inputData.getString(FALLO)))

    }
    suspend fun longFunction(){
        for(count in 0..20){
            Log.d("webservice", "Coroutine ..... $count ... ${Thread.currentThread().name}")
            delay(1000)

        }
    }




}