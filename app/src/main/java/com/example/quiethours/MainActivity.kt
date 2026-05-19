package com.example.quiethours

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quiethours.ui.theme.Scheduler

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestDndPermission()
        Scheduler.schedule(this)
        setContent {
            var enabled by remember { mutableStateOf(false) }

            val controller = DndController(this)

            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text("QuietHours")

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        enabled = !enabled

                        if (enabled) {
                            controller.enableQuietHours()
                        } else {
                            controller.disableQuietHours()
                        }
                    }) {
                        Text(if (enabled) "Disable Quiet Hours" else "Enable Quiet Hours")
                    }
                }
            }
        }
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