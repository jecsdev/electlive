package com.jecsdev.eleclive.ui.views.components

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.ui.theme.PurpleGrey40

@Composable
fun ElectionCard(context: Context, modifier: Modifier) {
    val defaultColor = if(!isSystemInDarkTheme()){
        CardDefaults.cardColors(containerColor = Color.White)
    }else{
        CardDefaults.cardColors(containerColor = PurpleGrey40)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp),
        colors = defaultColor,
        elevation = CardDefaults.elevatedCardElevation(8.dp),
    ) {
        Box(
            modifier
                .padding(12.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Row {
                Text(
                    text = context.getString(R.string.press_for_scan),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.width(8.dp))
                Icon(painter = painterResource(R.drawable.baseline_document_scanner_24), contentDescription = null)
            }

        }
    }
}