package com.example.workmanager.workManager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager.MainActivity

class DemoWorkManager(appContext : Context, workerParams: WorkerParameters) : Worker(appContext, workerParams)
{

    val TAG = "DemoWorkManagerTag"

    // STATIC VARIABLE
    companion object{
        @JvmStatic
        val KEY_WORKER = "Sent"
    }

    /**
     * The doWork() method runs asynchronously
     * on a background thread provided by WorkManager.
     * */
    override fun doWork(): Result
    {
        // The WorkManager stuff to handle comes here!
        // Getting Data from InputData


        // Do the work here--in this case, count to the passed number:

        // Getting Data from InputData
        val data = inputData
        val countingLimit = data.getInt(MainActivity.KEY_COUNTER_VALUE, 0)
        Log.i(TAG, "doWork: COUNTING LIMIT -> $countingLimit")

        for (i in 0 until countingLimit) {
            Log.i("TAGY", "Count is : $i")
        }

        // Sending Data and Done info

        // Sending Data and Done info
        val dataToSend = Data.Builder()
            .putString(KEY_WORKER, "Task Done Successfully")
            .build()

        // The Result returned from doWork() informs the
        // WorkManager service whether the work succeeded and,
        // in the case of failure, whether or not the work should be retried.
        //  - Result.success(): The work finished successfully.
        //  - Result.failure(): The work failed.
        //  - Result.retry():   The work failed and should be tried at
        //                      another time according to its retry policy

        // The Result returned from doWork() informs the
        // WorkManager service whether the work succeeded and,
        // in the case of failure, whether or not the work should be retried.
        //  - Result.success(): The work finished successfully.
        //  - Result.failure(): The work failed.
        //  - Result.retry():   The work failed and should be tried at
        //                      another time according to its retry policy
        return Result.success(dataToSend)
    }
}