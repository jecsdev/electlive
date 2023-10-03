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

    // This flag captures the scanning event preventing duplicate data
    private var isScanning = true

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
        val regex = "[^0-9]".toRegex()
        val emptyString = context.getString(R.string.empty_string)
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->

                    if (isScanning) {
                        val code = barcodes.firstOrNull()
                        if (code?.format == Barcode.FORMAT_CODABAR) {
                            val codeScanned = code.displayValue?.replace(
                                regex,
                                emptyString
                            )
                                ?: emptyString
                            if (codeScanned.length != 11) {
                                showToast(codeScanned, 3000)

                            } else {
                                val voting = Voting(emptyString, codeScanned, emptyString, emptyString)

                                barcodeViewModel.createVoting(voting)

                                showToast(context.getString(R.string.scan_successful), 10000)
                                Log.i(SCAN_RESULT, voting.toString())

                                isScanning = false
                                callback()
                            }

                        } else {
                            showToast(context.getString(R.string.barcode_not_allowed), 1000)
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
     * @param toastInterval handles the seconds that toast is showing on screen
     */

    @SuppressLint("ShowToast")
    private fun showToast(message: String, toastInterval: Long) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
        handler.postDelayed({
            toast.cancel()
        }, toastInterval)
    }
}
