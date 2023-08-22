package com.jecsdev.eleclive.ui.views.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.eleclive.R
import com.jecsdev.eleclive.core.network.ApiResponse
import com.jecsdev.eleclive.ui.viewModels.MainViewModel
import com.jecsdev.eleclive.ui.views.components.ElectionCard
import com.jecsdev.eleclive.utils.AppConstants.RESPONSE


@Composable
fun ElectionsScreen(mainViewModel: MainViewModel) {
    val elections = mainViewModel.electionsFlow.collectAsState(ApiResponse.Loading)


    Column {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 28.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        when(elections.value){
            is ApiResponse.Successful -> {
                LazyColumn {
                    (elections.value as ApiResponse.Successful).data?.let { electionsList ->
                        items(electionsList, key = { item -> item.id }) { election ->
                            ElectionCard(
                                name = election.name, description = election.description, date = election.date
                            )
                        }
                    }
                }
            }

            is ApiResponse.Error -> {
                Log.e(RESPONSE, "Error")
            }
            is ApiResponse.Loading -> {

            }
        }

    }
}