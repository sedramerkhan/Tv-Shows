package com.example.moviesjetpackcompose.presentation.tvShowsList.Components


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.moviesjetpackcompose.R


@ExperimentalComposeUiApi
@Composable
fun TopAppBar1(
    searchWidgetState: SearchWidgetState,
    query: String,
    isDark: Boolean,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onToggleTheme: () -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered,
                isDark = isDark,
                onToggleTheme = onToggleTheme
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                query = query,
                onQueryChanged = onQueryChanged,
                onCloseClicked = onCloseClicked,
                onExecuteSearch = onExecuteSearch,
                isDark = isDark,
                onToggleTheme = onToggleTheme
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "TvShows",
                style = MaterialTheme.typography.h3
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClicked,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
//                    tint = MaterialTheme.colors.surface
                )
            }
            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier
                    .size(31.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Crossfade(targetState = isDark) { isDark ->
                    val id = if (isDark) R.drawable.sun else R.drawable.moon
                    val tint = if (isDark) Color.White else Color.Black
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = "Toggle Dark/Light Theme",
                        tint = tint
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onExecuteSearch: () -> Unit,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .weight(.8f),
                value = query,
                onValueChange = {
                    onQueryChanged(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = "Search here...",
                        color = Color.White
                    )
                },
                textStyle = MaterialTheme.typography.h2,
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (query.isNotEmpty()) {
                                onQueryChanged("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.White
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onExecuteSearch()
                        keyboardController?.hideSoftwareKeyboard()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
//                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                ),
            )

            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 9.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Crossfade(targetState = isDark) { isDark ->
                    val id = if (isDark) R.drawable.sun else R.drawable.moon
                    val tint = if (isDark) Color.White else Color.Black
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = "Toggle Dark/Light Theme",
                        tint = tint
                    )
                }
            }

        }


    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {}, true, {})
}


