package com.example.quiethours.ui.theme

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object Scheduler {

    fun schedule(context: Context) {

        val enableRequest =
            OneTimeWorkRequestBuilder<EnableQuietHoursWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()

        val disableRequest =
            OneTimeWorkRequestBuilder<DisableQuietHoursWorker>()
                .setInitialDelay(20, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(context)
            .enqueue(enableRequest)

        WorkManager.getInstance(context)
            .enqueue(disableRequest)
    }
}