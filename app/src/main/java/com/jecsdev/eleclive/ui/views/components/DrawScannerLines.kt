package com.jecsdev.eleclive.ui.views.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawScannerLines(viewWidth: Int, viewHeight: Int) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val lineWidth = 2.dp.toPx()
            val lineColor = Color.Red

            val centerX = viewWidth / 2f
            val centerY = viewHeight / 2f

            val startX = centerX - 250.dp.toPx()
            val stopX = centerX + 250.dp.toPx()

            drawLine(
                color = lineColor,
                strokeWidth = lineWidth,
                start = Offset(startX, centerY),
                end = Offset(stopX, centerY)
            )
        }
    }
}