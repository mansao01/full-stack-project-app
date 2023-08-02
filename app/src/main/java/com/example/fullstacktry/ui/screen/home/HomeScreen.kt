package com.example.fullstacktry.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NaturePeople
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fullstacktry.network.response.DataItem
import com.example.fullstacktry.ui.common.HomeUiState
import com.example.fullstacktry.ui.component.ErrorScreen
import com.example.fullstacktry.ui.component.ListItem
import com.example.fullstacktry.ui.component.LoadingScreen
import com.example.fullstacktry.ui.component.LoadingScreenWithText
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
//    will call getUserList when changed to home screen
    LaunchedEffect(Unit) {
        homeViewModel.getUserList()
    }

    when (uiState) {
        is HomeUiState.Loading -> LoadingScreenWithText()
        is HomeUiState.Success -> {
            RefreshData(userList = uiState.profile.data, homeViewModel = homeViewModel, modifier = modifier)
        }

        is HomeUiState.Error -> ErrorScreen()
    }
}

@Composable
fun UserList(
    userList: List<DataItem>,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (userList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.NaturePeople,
                contentDescription = null,
                modifier = Modifier.size(62.dp)

            )
            Text(text = "Seems you don't have any data")
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(userList) { data ->
                ListItem(
                    id = data.id,
                    name = data.name,
                    age = data.age,
                    address = data.address,
                    homeViewModel,
                    modifier = modifier.clickable {
                        mToast(context, data.name)
                    })

            }
        }
    }

}

@Composable
fun RefreshData(
    userList: List<DataItem>,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val isLoading by homeViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { homeViewModel.getUserList() },
        modifier = modifier
    ) {
        UserList(userList = userList, homeViewModel)
    }
}

private fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}


