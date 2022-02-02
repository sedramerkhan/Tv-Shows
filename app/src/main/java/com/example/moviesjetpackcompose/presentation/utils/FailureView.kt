package com.example.moviesjetpackcompose.presentation.tvShowDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.R
import com.example.moviesjetpackcompose.presentation.theme.Teal700

@Composable
fun FailureView(
    isDark: Boolean
){


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
           painter = painterResource(id = R.drawable.warning),
            contentDescription = "Warning Icon",
            tint = Teal700,
            modifier = Modifier.size(150.dp).alpha(.5f)
        )
        Text(
            text = "Failed to get information",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
    }
}