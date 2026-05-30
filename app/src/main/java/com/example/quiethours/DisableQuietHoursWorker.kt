package com.example.quiethours

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DisableQuietHoursWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val controller = DndController(applicationContext)
        controller.disableQuietHours()

        return Result.success()
    }
}