package com.jecsdev.eleclive.ui.views.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.ui.views.components.ElectionCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun ElectionsScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(it)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            ElectionCard()
        }

    }
}