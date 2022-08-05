package com.vila.workmanager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import java.util.*

class MyWork(context:Context, params: WorkerParameters) :Worker (context,params){

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)


    companion object{
        var timerTask : TimerTask? = null
        val handler =  Handler(Looper.getMainLooper())
        var runnable : Runnable? = null
    }


    init {
        timerTask?.cancel()
         timerTask = object :TimerTask() {
            override fun run() {
                for (count in 0..20) {
                    Log.d("webservice", "Timer ..... $count ")
                    Thread.sleep(1000)
                }
            }
        }

       /* runnable = Runnable {

                for (count in 0..20) {
                    Log.d("webservice", "Coroutine ..... $count ${Thread.currentThread().name}")
                    Thread.sleep(1000)
                    handler.postDelayed(runnable!!, 60000)

            }
        }

        handler.removeCallbacks(runnable!!)*/
    }


    override fun doWork(): Result {
      /* scope.launch {
           while (isActive){
               coroutineTest()
               delay(60000)

           }
        }*/

       // Log.d("webservice","dentro  de coroutine ")

        val timer = Timer()
        timer.schedule(timerTask,0,60000)

        //handler.post(runnable!!)

        return Result.success()
    }





    private suspend  fun coroutineTest(){
        withContext(Dispatchers.IO){
            /*Log.d("webservice", "Coroutine ${coroutineContext} ")
            Log.d("webservice", " ")*/
            for(count in 0..20){
                Log.d("webservice", "simple ..... $count ... ${Thread.currentThread().name}")
                delay(1000)

            }
            Log.d("webservice", "fin de la coroutina ")

        }
    }

    override fun onStopped() {
        super.onStopped()
       /* Log.d("webservice", "-------------------- ")
        Log.d("webservice", "Estoy en onStopped dentro del worker")

        Log.d("webservice", "Cancel coroutine")*/
        job.cancel()
    }



}
