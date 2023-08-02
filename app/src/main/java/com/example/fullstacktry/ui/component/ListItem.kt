package com.example.fullstacktry.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fullstacktry.ui.screen.home.HomeViewModel

@Composable
fun ListItem(
    id: Int,
    name: String,
    age: Int,
    address: String,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = name, modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )
            Text(
                text = age.toString(),
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )
            Text(
                text = address, modifier = modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )

            IconButton(onClick = {
                homeViewModel.deleteProfile(id)
                homeViewModel.getUserList()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete data")
            }
        }
    }
}