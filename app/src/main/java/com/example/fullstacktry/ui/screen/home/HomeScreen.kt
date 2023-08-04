package com.example.fullstacktry.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NaturePeople
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fullstacktry.R
import com.example.fullstacktry.network.response.DataItem
import com.example.fullstacktry.ui.common.HomeUiState
import com.example.fullstacktry.ui.component.ErrorScreen
import com.example.fullstacktry.ui.component.ListItem
import com.example.fullstacktry.ui.component.LoadingScreenWithText
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.Locale


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    navigateToUpdate: (Int) -> Unit,
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
            RefreshData(
                userList = uiState.profile.data,
                homeViewModel = homeViewModel,
                navigateToUpdate = navigateToUpdate,
                modifier = modifier
            )
        }

        is HomeUiState.Error -> ErrorScreen()
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(id = title),
//            text = stringResource(id = title).uppercase(Locale.getDefault()),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top  = 48.dp)
                .paddingFromBaseline(top = 40.dp, bottom = 26.dp)
        )
        content()
    }
}

@Composable
fun UserList(
    userList: List<DataItem>,
    homeViewModel: HomeViewModel,
    navigateToUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

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
            Text(text = stringResource(R.string.seems_you_don_t_have_any_data_please_create_first))
        }
    } else {
        HomeSection(title = R.string.profile) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                items(userList) { data ->
                    ListItem(
                        id = data.id,
                        name = data.name,
                        age = data.age,
                        address = data.address,
                        homeViewModel,
                        modifier = modifier.clickable {
                            navigateToUpdate(data.id)
                        })

                }
            }
        }
    }

}

@Composable
fun RefreshData(
    userList: List<DataItem>,
    homeViewModel: HomeViewModel,
    navigateToUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoading by homeViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { homeViewModel.getUserList() },
        modifier = modifier
    ) {
        UserList(userList = userList, homeViewModel, navigateToUpdate)
    }
}



