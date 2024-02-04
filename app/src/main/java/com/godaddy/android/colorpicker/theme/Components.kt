// ktlint-disable filename
package com.godaddy.android.colorpicker.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.godaddy.android.colorpicker.R

@Composable
fun BackButton(onBackPress: () -> Unit) {
    IconButton(
        onClick = {
            onBackPress()
        }
    ) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.content_description_back_button)
        )
    }
}
