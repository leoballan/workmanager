package com.vila.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class CoroutineLongWork(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext,params) {

    private val _notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    override suspend fun doWork(): Result {
        withContext(Dispatchers.Default) {

            val progress = "LongWorker in Progress"
           setForeground(createForegroundInfo(progress))
            while (isActive) {
                longWork()
                delay(60000)
            }
        }
        return Result.success()
    }


     fun createForegroundInfo(progress:String): ForegroundInfo {
        val id = "Notification ID"
        val title = "Notification Tittle"
        val cancel = "Notification Cancel"
        val pendingIntent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val name = "Notification Channel Name"
            val descriptionText = "Channel descripion"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            _notificationManager.createNotificationChannel(mChannel)
        }

        val notification = NotificationCompat.Builder(applicationContext,id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .addAction(R.drawable.ic_baseline_cancel,cancel,pendingIntent)
            .build()

        return ForegroundInfo(100,notification)
    }



    private suspend fun longWork(){

            for (count in 0 until 100){
                Log.d("webservice","inside long function LongCoroutine ... $count " +
                        "...${Thread.currentThread().name}")
                delay(1000)
            }

    }
}