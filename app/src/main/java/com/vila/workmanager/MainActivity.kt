package com.vila.workmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.work.WorkManager
import androidx.work.*
import com.vila.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var workManager : WorkManager
    //lateinit var workRequest : OneTimeWorkRequest
    private var lastTimeClick = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }
    private fun init(){

        workManager = WorkManager.getInstance(this)
       /* workRequest  = OneTimeWorkRequest.Builder(MyWork::class.java)
            //.setConstraints(constrains)
            .build()

        workRequest = OneTimeWorkRequest.from(MyWork::class.java)
*/
        initListeners()
        initObservers()

    }


    private fun initListeners() {

        binding.btSimpleWork.setOnClickListener {
            Log.d("webservice","start simple work ... ")
            configSimpleWroker(MyWork::class.java)
        }

        binding.btStopSimpleWork.setOnClickListener {
            Log.d("webservice","stop simple work ... ")
            workManager.cancelUniqueWork(MyWork::class.java.simpleName)
        }

        binding.btCoroutineWork.setOnClickListener {
            Log.d("webservice","start coroutine work ... ")
            configWorker(
                "start MyCoroutineWork from button -----------"
                , MyCoroutineWork::class.java)
        }

        binding.btStopCoroutineWork.setOnClickListener {
            Log.d("webservice","stop coroutine work ... ")
            workManager.cancelUniqueWork(MyCoroutineWork::class.java.simpleName)
        }

        binding.btLongCoroutine.setOnClickListener {
            Log.d("webservice","start Long coroutine work ... ")
            configWorker(
                "start CoroutineLongWork from button -----------"
                , CoroutineLongWork::class.java)
        }

        binding.btStopLongCoroutine.setOnClickListener {
            Log.d("webservice","stop Long coroutine work ... ")
            workManager.cancelUniqueWork(CoroutineLongWork::class.java.simpleName)
        }

        binding.btCoroutineWorkHandler.setOnClickListener{
            Log.d("webservice","start coroutine work handler... ")
            configWorker(
                "start MyCoroutineWorkmanager WITH HANDLER from button -----------"
                , MyCoroutineWorkWithHandler::class.java)
        }

        binding.btStopWorkHandler.setOnClickListener {
            Log.d("webservice","stop coroutine work handler... ")
            workManager.cancelUniqueWork(MyCoroutineWorkWithHandler::class.java.simpleName)
        }

        ////////////////////// other buttons clicks //////////////////////////

        binding.btDoubleClick.setOnClickListener {
            if ((System.currentTimeMillis() - lastTimeClick) < 300L){
                Log.d("mControl","double click ... ")
            }else
            {
                Log.d("mControl","simple click ... ")
                lastTimeClick = System.currentTimeMillis()
            }
        }

        binding.btCustom.setOnClickCustomListener(
            object : MyCustomButtom.OnClickCustomListenerJava {
                override fun onCustomClick() {
                    Log.d("mcontrol", "mi customclick")
                }

            })

        binding.btCustomExtension.setOnDoubleClickListener {
            Log.d("mcontrol", "mi customclick")
        }


        binding.btNextActivity.setOnClickListener {
            Intent(this, SecondActivity::class.java).apply {
                startActivity(this) }
        }

    }



    private fun initObservers() {

        workManager.getWorkInfosForUniqueWorkLiveData(
            MyCoroutineWorkWithHandler::class.java.simpleName)
            .observe(this) {

                if (it.isNotEmpty()) {
                    when (it[0].state) {
                        WorkInfo.State.RUNNING -> {
                            Log.d("mControl", "dentro del observer HANDLER ... RUNNING")

                        }
                        WorkInfo.State.SUCCEEDED -> {
                            Log.d("mControl", "dentro del observer ... SUCCEDED")

                        }
                        WorkInfo.State.FAILED -> {
                            Log.d("mControl", "dentro del observer ... FINISHED")

                        }
                        else -> {}
                    }
                }
            }

        workManager.getWorkInfosForUniqueWorkLiveData(
            MyCoroutineWork::class.java.simpleName)
            .observeForever{

                if (it.isNotEmpty())
                when(it[0].state){
                    WorkInfo.State.SUCCEEDED ->{
                        Log.d("webservice","dentro del observer ... succeded")
                        val dato = it[0].outputData.getString(FALLO)
                        Log.d("webservice"
                            ,"valor del dato $dato")
                           //configWorker("now form observer",MyCoroutineWork::class.java)


                    }
                    WorkInfo.State.RUNNING ->{
                        Log.d("webservice","dentro del observer COMUN... RUNNING")

                    }
                    else ->{}
                }
            }



        workManager.getWorkInfosForUniqueWorkLiveData(MyWork::class.java.simpleName)
            /*.getWorkInfoByIdLiveData(workRequest.id)*/.observe(this) { workInfo ->

                Log.d("webservice", "dentro del observer")

                if (workInfo.isNotEmpty()) {
                    when (workInfo[0].state) {
                        WorkInfo.State.RUNNING -> {
                            binding.tvText.setText("Ejecutandose")
                            Log.d("webservice", "dentro del observer ... state running")
                        }
                        WorkInfo.State.SUCCEEDED -> {

                            Log.d("webservice", "dentro del observer ... state succeded")
                            /*    Log.d("mControl"
                                    ,"dentro del observer ... " +
                                            "${workInfo.outputData.getString(FALLO)}")
                                configWork("here we go again")*/
                            // if (workInfo.outputData.getString(FALLO).equals("OK"))
                            /*{
                                Log.d("mControl","dentro del observer ... state succeded")
                            }*/
                            //
                            // jbinding.tvText.setText("Termino Exitosamente")


                        }
                        WorkInfo.State.FAILED -> {
                            /* if (workInfo.outputData.getString(FALLO)== "hola")
                                 Log.d("webservice","el fallo fue detectado")*/
                        }
                        else -> {}
                    }
                }


            }

    }

    private fun configWorker(data:String, worker:Class<out CoroutineWorker>){

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED).build()


        val workRequest = OneTimeWorkRequest
            .Builder(worker)
            //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf(FALLO to data))
            //.setConstraints(constrains)
            .build()

        // distintas formas de crear un workRequest
        val mWorkRequest = OneTimeWorkRequestBuilder<MyWork>().build()
        val anotherWorkRequest = OneTimeWorkRequest.from(MyWork::class.java)



        workManager.enqueueUniqueWork(
            worker.simpleName
            ,ExistingWorkPolicy.REPLACE
            ,workRequest)

    }

    private fun configSimpleWroker(
        worker:Class <out Worker>
    ){
        Log.d("webservice", "------------ config worker -------- ")

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED).build()

        NetworkType.METERED
        NetworkType.CONNECTED
        NetworkType.UNMETERED

        val mworkRequest  = OneTimeWorkRequest.Builder(worker)
            .setConstraints(constrains)
            .build()


        workManager.enqueueUniqueWork(
            worker.simpleName
            ,ExistingWorkPolicy.REPLACE,
            mworkRequest
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        workManager.getWorkInfosForUniqueWorkLiveData(
            MyCoroutineWork::class.java.simpleName).removeObservers(this)
    }

    companion object{
        val FALLO  = "fallo"
    }

}