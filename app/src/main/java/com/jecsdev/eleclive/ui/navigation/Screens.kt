package com.jecsdev.eleclive.ui.navigation

sealed class Screens(val route: String){
    object  ElectionsScreen: Screens("elections_screen")
    object  BarcodeScanner: Screens("barcode_scanner")
}
