package com.jecsdev.eleclive.utils.analyzer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.data.model.Voting
import com.jecsdev.eleclive.ui.viewModels.BarcodeViewModel
import com.jecsdev.eleclive.utils.constants.AppConstants.SCAN_FAILURE
import com.jecsdev.eleclive.utils.constants.AppConstants.SCAN_RESULT
import com.jecsdev.eleclive.utils.constants.AppConstants.TOAST_INTERVAL
import com.jecsdev.eleclive.utils.providers.GetResourceProvider


/**
 * This class handle the BarcodeScanner process
 * @param context is the activity context
 * @param callback retrieves the action after code scanned
 */
@ExperimentalGetImage
class BarcodeAnalyser(
    private val context: Context,
    private val barcodeViewModel: BarcodeViewModel,
    val callback: () -> Unit
) : ImageAnalysis.Analyzer {

    // Preparing the handler for toast time control and resource providers

    private val handler = Handler(Looper.getMainLooper())
    private val resourceProvider = GetResourceProvider(context)

    /**
     * This method prepare all the necessary for the scanning
     * @param imageProxy handle the camera captures
     */
    override fun analyze(imageProxy: ImageProxy) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_CODABAR)
            .build()

        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.forEach { code ->
                        if (code.format == Barcode.FORMAT_CODABAR) {
                            val codeScanned = code.rawValue
                                ?: resourceProvider.getString(R.string.empty_string)
                            val voting = Voting(code.hashCode(), "Usuario demo", codeScanned, "1002", "Colegio demo")
                            barcodeViewModel.createVoting(voting)
                            showToast(codeScanned)
                            Log.i(SCAN_RESULT, codeScanned)
                            callback()
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.barcode_not_allowed), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(SCAN_FAILURE, exception.message.toString())
                }


        }
        imageProxy.close()
    }

    /**
     * This method shows a toast with a determined value handling the time displayed
     * @param message is the message that's going to be showed depending the case
     */

    @SuppressLint("ShowToast")
    private fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
        handler.postDelayed({
            toast.cancel()
        }, TOAST_INTERVAL)
    }
}
