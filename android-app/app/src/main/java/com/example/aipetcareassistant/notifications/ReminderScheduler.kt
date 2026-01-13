package com.example.aipetcareassistant.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun scheduleReminder(context: Context, reminderId: String, petName: String, title: String, delayMinutes: Long) {
        val data = Data.Builder()
            .putString("reminderId", reminderId)
            .putString("petName", petName)
            .putString("title", title)
            .build()

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}

// TODO: Replace WorkManager with exact AlarmManager or FCM for advanced scheduling.
