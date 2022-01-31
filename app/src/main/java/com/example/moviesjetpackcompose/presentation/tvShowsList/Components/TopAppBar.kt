package com.example.moviesjetpackcompose.presentation.tvShowsList.Components


import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.moviesjetpackcompose.R


@ExperimentalComposeUiApi
@Composable
fun TopAppBar1(
    searchWidgetState: SearchWidgetState,
    query: String,
    isDark: Boolean,
    startAnimation: Boolean = true,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    onToggleTheme: () -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                isDark = isDark,
                startAnimation = startAnimation,
                onToggleTheme = onToggleTheme,
                onSearchClicked = onSearchTriggered,
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                query = query,
                isDark = isDark,
                onQueryChanged = onQueryChanged,
                onExecuteSearch = onExecuteSearch,
                onCloseClicked = onCloseClicked,
                onToggleTheme = onToggleTheme
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    isDark: Boolean,
    startAnimation: Boolean = true,
    onToggleTheme: () -> Unit,
    onSearchClicked: () -> Unit,
) {

    val configuration = LocalConfiguration.current
    val animStart  = when(configuration.orientation){
      Configuration.ORIENTATION_PORTRAIT -> -337.dp
      else -> -737.dp
    }
    val animEnd = 0.dp
    val pos = animateDpAsState(
        targetValue = if (startAnimation) animStart else animEnd,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alpha = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f,
        animationSpec = tween(
            durationMillis = 900
        )
    )

    TopAppBar(
        title = {
            Text(
                text = "TvShows",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .alpha(alpha = alpha.value),
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClicked,
                modifier = Modifier
                    .size(30.dp)
                    .absoluteOffset(x = pos.value)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
//                    tint = Color.White
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
    isDark: Boolean,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onCloseClicked: () -> Unit,
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
                textStyle = MaterialTheme.typography.h5,
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
                    textColor = Color.White,
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


