package com.example.fullstacktry.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fullstacktry.network.response.DataItem
import com.example.fullstacktry.ui.common.HomeUiState
import com.example.fullstacktry.ui.component.ErrorScreen
import com.example.fullstacktry.ui.component.ListItem
import com.example.fullstacktry.ui.component.LoadingScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
//    will call getUserList when change screen
    LaunchedEffect(Unit) {
        homeViewModel.getUserList()
    }

    when (uiState) {
        is HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success -> {
//            UserList(userList = uiState.profile.data)
            RefreshData(userList = uiState.profile.data, homeViewModel = homeViewModel)
        }


        is HomeUiState.Error -> ErrorScreen()
    }

}

@Composable
fun UserList(
    userList: List<DataItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(userList) {
            ListItem(
                name = it.name,
                age = it.age,
                address = it.address,
                modifier = modifier.clickable {
                    mToast(context, it.name)
                })
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
        onRefresh = {homeViewModel.getUserList() },
    ) {
        UserList(userList = userList)
    }
}

private fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}


