package com.jecsdev.eleclive.ui.views.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner

import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.ui.views.components.CameraPreview
import com.jecsdev.eleclive.utils.AppConstants.CAMERA_PERMISSION
import com.jecsdev.eleclive.utils.GetResourceProvider


@Composable
fun BarcodeScanner() {
    val context = LocalContext.current
    val resourceProvider = GetResourceProvider(context)

    var scannedData by remember { mutableStateOf(resourceProvider.getString(R.string.empty_string)) }
    val cameraPermission = Manifest.permission.CAMERA

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, start the barcode scanning process
            } else {
                // Permission denied, handle accordingly
                scannedData = resourceProvider.getString(R.string.permission_denied)
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = scannedData)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            checkAndRequestCameraPermission(
                context = context,
                permission = cameraPermission,
                launcher = requestPermissionLauncher
            )
        }) {
            Text(text = resourceProvider.getString(R.string.scan_barcode))
        }

        CameraPreview(context, LocalLifecycleOwner.current, onScanResult = { newData ->
            scannedData = newData
        })
    }
}

fun checkAndRequestCameraPermission(
    context: Context, permission: String, launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        // Open camera because permission is already granted
        launcher.launch(permission)
    } else {
        // Request the permission
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(permission),
            CAMERA_PERMISSION
        )
    }
}

