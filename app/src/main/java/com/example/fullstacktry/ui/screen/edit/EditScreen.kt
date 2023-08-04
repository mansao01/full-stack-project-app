@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.fullstacktry.ui.screen.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fullstacktry.R
import com.example.fullstacktry.network.request.UpdateProfileRequest
import com.example.fullstacktry.ui.common.UpdateUiState
import com.example.fullstacktry.ui.component.ProgressbarDialog


@Composable
fun EditScreen(
    id: Int,
    uiState: UpdateUiState,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    editViewModel: EditViewModel = viewModel(factory = EditViewModel.Factory)
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        editViewModel.getProfileById(id)
    }

    when (uiState) {
        is UpdateUiState.Loading -> ProgressbarDialog()
        is UpdateUiState.StandBy -> EditScreenContent(
            editViewModel = editViewModel,
            navigateToHome = navigateToHome,
            id = id,
            nameValue = uiState.getProfileByIdResponse.name,
            ageValue = uiState.getProfileByIdResponse.age,
            addressValue = uiState.getProfileByIdResponse.address
        )

        is UpdateUiState.Success -> {
            uiState.updateData.msg?.let { mToast(context, it) }


        }

        is UpdateUiState.Error -> mToast(context, uiState.message)
        is UpdateUiState.ErrorGetProfileData -> mToast(
            context,
            stringResource(R.string.failed_to_get_profile_s_data)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreenContent(
    editViewModel: EditViewModel,
    navigateToHome: () -> Unit,
    id: Int,
    nameValue: String,
    ageValue: Int,
    addressValue: String,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 16.dp)
                .clickable { navigateToHome() })
        Text(
            text = stringResource(R.string.edit_data),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 48.dp)
        )
        var name by remember {
            mutableStateOf(nameValue)
        }
        var isNameNull by remember {
            mutableStateOf(false)
        }

        var age by remember {
            mutableStateOf(ageValue.toString())
        }
        var isAgeNull by remember {
            mutableStateOf(false)
        }

        var address by remember {
            mutableStateOf(addressValue)
        }
        var isAddressNull by remember {
            mutableStateOf(false)
        }
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_name))
            },
            placeholder = { Text(text = stringResource(R.string.name)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isNameNull,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_age))
            },
            placeholder = { Text(text = stringResource(R.string.age)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = null
                )
            },
            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isAgeNull,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )


        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            label = {
                Text(text = stringResource(R.string.enter_your_address))
            },
            placeholder = { Text(text = stringResource(R.string.address)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null
                )
            },

            supportingText = {
                Text(text = stringResource(R.string.require))
            },
            isError = isAddressNull,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        Button(
            onClick = {
                when {
                    name.isEmpty() -> isNameNull = true
                    age.isEmpty() -> isAgeNull = true
                    address.isEmpty() -> isAddressNull = true
                    else -> {
                        editViewModel.updateProfileData(
                            id,
                            UpdateProfileRequest(
                                name,
                                age.toInt(),
                                address
                            )
                        )
                        navigateToHome()
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 52.dp)
                .padding(top = 18.dp)
        ) {
            Text(text = "Post")
        }
    }
}

private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
