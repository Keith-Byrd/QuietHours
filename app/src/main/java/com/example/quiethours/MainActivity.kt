package com.example.quiethours

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestDndPermission()

        setContent {

            var showStartPicker by remember { mutableStateOf(false) }
            var showEndPicker by remember { mutableStateOf(false) }

            var startHour by remember { mutableStateOf(loadStartHour()) }
            var startMinute by remember { mutableStateOf(loadStartMinute()) }

            var endHour by remember { mutableStateOf(loadEndHour()) }
            var endMinute by remember { mutableStateOf(loadEndMinute()) }

            val controller = DndController(this)

            var enabled by remember {
                mutableStateOf(controller.isQuietHoursEnabled())
            }

            MaterialTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "QuietHours",
                        style = MaterialTheme.typography.displayLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // MAIN TOGGLE
                    Button(
                        onClick = {
                            enabled = !enabled
                            saveEnabledState(enabled)

                            if (enabled) {
                                controller.enableQuietHours()

                                Scheduler.scheduleQuietHours(
                                    this@MainActivity,
                                     startHour, startMinute,
                                    endHour, endMinute
                                )

                            } else {
                                controller.disableQuietHours()
                                Scheduler.cancelQuietHours(this@MainActivity)
                            }
                        },
                                modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (enabled) "DISABLE" else "ENABLE",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        saveTimes(22, 0, 7, 0)
                        startHour = 22
                        startMinute = 0
                        endHour = 7
                        endMinute = 0
                    },modifier = Modifier.fillMaxWidth()
                    ){
                        Text("Default 10PM–7AM",
                        style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { showStartPicker = true },
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = String.format(
                                Locale.getDefault(),
                                "Set Start Time (%02d:%02d)",
                                startHour,
                                startMinute
                            ),
                        style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = { showEndPicker = true },
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = String.format(
                                Locale.getDefault(),
                                "Set End Time (%02d:%02d)",
                                endHour,
                                endMinute
                            ),
                        style = MaterialTheme.typography.headlineLarge
                        )
                    }

                }

                val startState = rememberTimePickerState(
                    initialHour = startHour,
                    initialMinute = startMinute,
                    is24Hour = true
                )

                val endState = rememberTimePickerState(
                    initialHour = endHour,
                    initialMinute = endMinute,
                    is24Hour = true
                )

                // START TIME PICKER
                if (showStartPicker) {

                    AlertDialog(
                        onDismissRequest = { showStartPicker = false },
                        confirmButton = {
                            TextButton(onClick = {

                                startHour = startState.hour
                                startMinute = startState.minute

                                saveTimes(startHour, startMinute, endHour, endMinute)

                                Scheduler.scheduleQuietHours(
                                    this@MainActivity,
                                    startHour, startMinute,
                                    endHour, endMinute
                                )

                                showStartPicker = false
                            }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showStartPicker = false }) {
                                Text("Cancel")
                            }
                        },
                        text = {
                            TimePicker(state = startState)
                        }
                    )
                }
                // END TIME PICKER
                if (showEndPicker) {

                    AlertDialog(
                        onDismissRequest = { showEndPicker = false },
                        confirmButton = {
                            TextButton(onClick = {

                                endHour = endState.hour
                                endMinute = endState.minute

                                saveTimes(startHour, startMinute, endHour, endMinute)

                                Scheduler.scheduleQuietHours(
                                    this@MainActivity,
                                    startHour, startMinute,
                                    endHour, endMinute
                                )

                                showEndPicker = false
                            },modifier = Modifier.fillMaxWidth(0.9f)) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showEndPicker = false }) {
                                Text("Cancel")
                            }
                        },
                        text = {
                            TimePicker(state = endState)
                        }
                    )
                }
            }
        }
    }

    private fun saveTimes(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        val prefs = getSharedPreferences("quiethours", MODE_PRIVATE)
        prefs.edit()
            .putInt("startHour", startHour)
            .putInt("startMinute", startMinute)
            .putInt("endHour", endHour)
            .putInt("endMinute", endMinute)
            .apply()
    }

    private fun loadStartHour() =
        getSharedPreferences("quiethours", MODE_PRIVATE).getInt("startHour", 22)

    private fun loadStartMinute() =
        getSharedPreferences("quiethours", MODE_PRIVATE).getInt("startMinute", 0)

    private fun loadEndHour() =
        getSharedPreferences("quiethours", MODE_PRIVATE).getInt("endHour", 7)

    private fun loadEndMinute() =
        getSharedPreferences("quiethours", MODE_PRIVATE).getInt("endMinute", 0)

    private fun saveEnabledState(enabled: Boolean) {
        val prefs = getSharedPreferences("quiethours", MODE_PRIVATE)
        prefs.edit().putBoolean("enabled", enabled).apply()
    }

    private fun loadEnabledState(): Boolean {
        val prefs = getSharedPreferences("quiethours", MODE_PRIVATE)
        return prefs.getBoolean("enabled", false)
    }

    private fun requestDndPermission() {
        val nm = getSystemService(NotificationManager::class.java)

        if (!nm.isNotificationPolicyAccessGranted) {
            startActivity(
                Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            )
        }
    }
}