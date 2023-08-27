package com.jecsdev.eleclive.ui.navigation

sealed class Screens(val route: String){
    object  ElectionsScreen: Screens("elections_screen")
    object  BarcodeScannerScreen: Screens("barcode_scanner")
    object  CameraPreviewScreen: Screens("camera_preview")
}
