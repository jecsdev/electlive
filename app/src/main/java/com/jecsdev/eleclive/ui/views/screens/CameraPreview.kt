package com.jecsdev.eleclive.ui.views.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.jecsdev.eleclive.ui.viewModels.BarcodeViewModel
import com.jecsdev.eleclive.ui.views.components.DrawScannerLines
import com.jecsdev.eleclive.utils.analyzer.BarcodeAnalyser
import com.jecsdev.eleclive.utils.constants.AppConstants.EXCEPTION
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun BarcodeScanner(
    navController: NavController,
    barcodeViewModel: BarcodeViewModel
) {

    AndroidView(
        factory = { context ->
            // Create an executor to manage camera-related operations on a single background thread.
            val cameraExecutor = Executors.newSingleThreadExecutor()

            // Create a PreviewView instance to display the camera preview.
            // Set the scale type to ensure the preview fills the view while maintaining the aspect ratio.
            val previewView = PreviewView(context).also { preview ->
                preview.scaleType = PreviewView.ScaleType.FIT_CENTER
            }

            // Obtain an instance of ProcessCameraProvider asynchronously.
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                // The callback is triggered when the camera provider instance is ready.

                // Get the actual ProcessCameraProvider instance from the future.
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                // Prepare the image preview
                val preview = Preview.Builder()
                    .build()
                    .also { preview ->
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                    }
                // Set image to capture
                val imageCapture = ImageCapture.Builder().build()

                // Prepare the image for scanning
                val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also { imageAnalysis ->
                        imageAnalysis.setAnalyzer(cameraExecutor, BarcodeAnalyser(context, barcodeViewModel) {
                            navController.navigateUp()
                        })

                    }

                // Sets camera selector
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        context as ComponentActivity,
                        cameraSelector,
                        preview,
                        imageCapture,
                        imageAnalyzer
                    )

                } catch (exc: Exception) {
                    Log.e(EXCEPTION, exc.message.toString())
                }
            }, ContextCompat.getMainExecutor(context))
            previewView
        },
        modifier = Modifier
            .size(width = 250.dp, height = 250.dp)
    )
        // Draw the Scanner lines
        DrawScannerLines(viewWidth = getPxFromDp(dp =364.dp), viewHeight = getPxFromDp(dp = 780.dp))
}

/***
 * @param dp is the dp value to convert to px
 */

@Composable
fun getPxFromDp(dp: Dp): Int {
    val density = LocalDensity.current.density
    return (dp.value * density).toInt()
}

