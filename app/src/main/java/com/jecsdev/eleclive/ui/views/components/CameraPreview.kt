package com.jecsdev.eleclive.ui.views.components

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    context: Context, lifecycleOwner: LifecycleOwner, onScanResult: (String) -> Unit
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val scanner = remember { BarcodeScanning.getClient() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }


    val cameraSelector =
        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

    val preview = Preview.Builder().build().also { preview ->
        preview.setSurfaceProvider(getPreviewSurfaceProvider())
    }

    val imageAnalyzer = ImageAnalysis.Builder().build().also { imageAnalysis ->
        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val width = imageProxy.width
            val height = imageProxy.height
            val yBuffer: ByteBuffer = imageProxy.planes[0].buffer
            val uvBuffer: ByteBuffer = imageProxy.planes[1].buffer

            val inputImage = InputImage.fromByteBuffer(
                yBuffer, width, height, rotationDegrees, InputImage.IMAGE_FORMAT_YV12
            )

            scanner.process(inputImage).addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    val result = barcodes.first().rawValue ?: "No se detectó ningún código"
                    onScanResult(result)
                } else {
                    onScanResult("No se detectó ningún código")
                }
            }.addOnFailureListener {exception ->
                onScanResult("Error: ${exception.localizedMessage}")
            }

            imageProxy.close()
        }
    }

    val useCaseGroup = UseCaseGroup.Builder().addUseCase(preview).addUseCase(imageAnalyzer).build()

    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, useCaseGroup
        )

        onDispose {
            cameraProvider.unbindAll()
            cameraExecutor.shutdown()
            scanner.close()
        }
    }
}


private fun getPreviewSurfaceProvider(): Preview.SurfaceProvider {
    return Preview.SurfaceProvider {}
}
