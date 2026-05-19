package com.example.quiethours.ui.theme

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quiethours.DndController

class EnableQuietHoursWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        DndController(applicationContext)
            .enableQuietHours()

        return Result.success()
    }
}