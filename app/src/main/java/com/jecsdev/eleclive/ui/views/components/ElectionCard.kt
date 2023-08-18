package com.jecsdev.eleclive.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.ui.theme.PurpleGrey40

@Preview(showSystemUi = true)
@Composable
fun ElectionCard() {
    val defaultColor = if(!isSystemInDarkTheme()){
        CardDefaults.cardColors(containerColor = Color.White)
    }else{
        CardDefaults.cardColors(containerColor = PurpleGrey40)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = defaultColor,
        elevation = CardDefaults.elevatedCardElevation(16.dp),
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = stringResource(R.string.election_name),
                modifier = Modifier.height(IntrinsicSize.Min),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.election_date),
                modifier = Modifier.height(IntrinsicSize.Min)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.election_description),
                modifier = Modifier.height(IntrinsicSize.Min)
            )
        }
    }
}