package com.example.quiethours

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class EnableQuietHoursWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val controller = DndController(applicationContext)
        controller.enableQuietHours()

        return Result.success()
    }
}