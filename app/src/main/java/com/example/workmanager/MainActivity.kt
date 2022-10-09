package com.example.workmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.workManager.DemoWorkManager

class MainActivity : AppCompatActivity() {

    // ------------- DATA MEMBERS ------------- //
    private lateinit var binding: ActivityMainBinding
    val TAG = "mainActivityTag"

    lateinit var countWorkRequest: WorkRequest

    // STATIC VARIABLE
    companion object {
        @JvmStatic
        val KEY_COUNTER_VALUE = "key_count"
    }

    // ------------- METHODS ------------- //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // DATA CREATION
        val data = Data.Builder().putInt(KEY_COUNTER_VALUE, 500).build()
        Log.i(TAG, "DATA: $data")
        countWorkRequest = OneTimeWorkRequest
                .Builder(DemoWorkManager::class.java)
                .setInputData(data)
                .build()

        // Let's Display the Status of Our Worker
        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(countWorkRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    Toast.makeText(
                        applicationContext,
                        "Status: " + workInfo.state.name,
                        Toast.LENGTH_SHORT
                    ).show()
                    if (workInfo.state.isFinished) {
                        val data1 = workInfo.outputData
                        val msg = data1.getString(DemoWorkManager.KEY_WORKER)
                        Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }

    /***
     * Once your work is defined, it must be scheduled with the WorkManager
     * service in order to run. WorkManager offers a lot of flexibility in how you schedule your work.
     *       1- You can schedule it to run periodically over an interval of time,
     *       2- or you can schedule it to run only one time.
     *
     * However you choose to schedule the work, you will always use a WorkRequest.
     * While a Worker defines the unit of work, a WorkRequest (and its subclasses) define how
     * and when it should be run. In the simplest case, you can use a OneTimeWorkRequest,
     * as shown in the following example.
     */
    // ON CLICK FUNCTION OF workButton
    fun startWorkBtnOnClick(view : View) {
        WorkManager.getInstance(applicationContext).enqueue(countWorkRequest)
    }

}