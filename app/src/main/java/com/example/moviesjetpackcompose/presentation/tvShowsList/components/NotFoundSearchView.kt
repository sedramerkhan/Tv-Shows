package com.example.moviesjetpackcompose.presentation.tvShowsList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.moviesjetpackcompose.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.moviesjetpackcompose.presentation.theme.Teal700

@Composable
fun NotFoundSearchView(
query: String,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Image(
                painter = painterResource(id = R.drawable.no_results) ,
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .alpha(.8f)
            )
        Text(
            text = "Nothing Match \"$query\"",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface
        )

    }
}