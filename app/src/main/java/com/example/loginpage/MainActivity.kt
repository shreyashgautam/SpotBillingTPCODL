package com.example.loginpage

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.ui.theme.LoginPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Notification channel for Android 8+
        createNotificationChannel(this)

        enableEdgeToEdge()
        setContent {
            LoginPageTheme {
                RequestNotificationPermissionIfNeeded()
                AppNavigation()
            }
        }
    }
}

// 🔔 Create Notification Channel
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "default_channel",
            "General Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }
}

// ✅ Compose function to request POST_NOTIFICATIONS permission
@Composable
fun RequestNotificationPermissionIfNeeded() {
    val context = LocalContext.current
    val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("PermissionCheck", "Permission granted by user")
        } else {
            Log.d("PermissionCheck", "Permission denied by user")
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Log.d("PermissionCheck", "Permission already granted")
            }
        }
    }
}

// 🧭 Navigation
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = context as? MainActivity

    LaunchedEffect(Unit) {
        val fromNotification = activity?.intent?.getBooleanExtra("fromNotification", false) == true
        if (fromNotification) {
            navController.navigate("reminder")
            // Clear the flag so it doesn't keep navigating again on recomposition
            if (activity != null) {
                activity.intent?.removeExtra("fromNotification")
            }
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { MobileOtp(navController) }
        composable("login2") { LoginPage(navController) }
        composable("home") {
            val ctx = LocalContext.current
            Home(navController = navController, context = ctx)
        }
        composable("dashboard") { DashboardUser(navController) }
        composable("reminder") { ReminderScreen() }
    }
}


// 🔔 Simple Reminder Screen (example)
@Composable
fun ReminderScreen() {
    androidx.compose.material3.Text(
        text = "Don't forget to watch this!",
        modifier = androidx.compose.ui.Modifier.padding(32.dp)
    )
}
