package com.jecsdev.eleclive.ui.views.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jecsdev.eleclive.ui.views.components.ElectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun ElectionsScreen(){
    Scaffold {it.calculateTopPadding()
        ElectionCard()
    }
}