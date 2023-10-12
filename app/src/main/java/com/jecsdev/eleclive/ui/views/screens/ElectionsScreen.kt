package com.jecsdev.eleclive.ui.views.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.ui.navigation.Screens
import com.jecsdev.eleclive.ui.viewModels.MainViewModel
import com.jecsdev.eleclive.ui.views.components.ElectionCard
import com.jecsdev.eleclive.utils.constants.AppConstants
import com.jecsdev.eleclive.utils.constants.NetworkConstants.RESPONSE
import com.jecsdev.eleclive.utils.providers.GetResourceProvider


@Composable
fun ElectionsScreen(navController: NavController, mainViewModel: MainViewModel) {
    val elections = mainViewModel.electionsFlow.collectAsState(ApiResponse.Loading)

    val context = LocalContext.current
    val resourceProvider = GetResourceProvider(context)

    var scannedData by remember { mutableStateOf(resourceProvider.getString(R.string.empty_string)) }
    val cameraPermission = Manifest.permission.CAMERA

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, start the barcode scanning process
                navController.navigate(Screens.BarcodeScanner.route)
            } else {
                // Permission denied, handle accordingly
                scannedData = resourceProvider.getString(R.string.permission_denied)
            }
        }

    Column {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        when (elections.value) {
            is ApiResponse.Successful -> {
                LazyColumn {
                    (elections.value as ApiResponse.Successful).data?.let { electionsList ->
                        items(electionsList, key = { item -> item.id }) {
                            ElectionCard(
                                context,
                                Modifier.clickable {
                                    checkAndRequestCameraPermission(
                                        context = context,
                                        permission = cameraPermission,
                                        launcher = requestPermissionLauncher
                                    )
                                }
                            )
                        }
                    }
                }
            }

            is ApiResponse.Error -> {
                Log.e(RESPONSE, (elections.value as ApiResponse.Error).message)
            }

            is ApiResponse.Loading -> {
                // Do nothing
            }

        }
    }
}


/**
 * This methods retrieves the current app version name
 * @param context to get the Instance from screen
 * @param permission permission parameter received
 * @param launcher manages the activity result for launch
 */
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
            AppConstants.CAMERA_PERMISSION
        )
    }
}