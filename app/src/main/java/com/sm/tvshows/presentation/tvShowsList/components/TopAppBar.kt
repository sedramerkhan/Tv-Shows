package com.sm.tvshows.presentation.tvShowsList.components


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sm.tvshows.R
import com.sm.tvshows.presentation.utils.getStatusBarPadding
import kotlinx.coroutines.delay



@ExperimentalComposeUiApi
@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    query: String,
    isDark: Boolean,
    startAnimation: Boolean = true,
    keyboardState: Boolean,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    onToggleTheme: () -> Unit,
) {
    Box {
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
                    keyboardState = keyboardState,
                    onQueryChanged = onQueryChanged,
                    onExecuteSearch = onExecuteSearch,
                    onCloseClicked = onCloseClicked,
                    onToggleTheme = onToggleTheme
                )
            }
        }
        WaveShape4()
//        WaveShape3()
    }

}

@Composable
fun DefaultAppBar(
    isDark: Boolean,
    startAnimation: Boolean = true,
    onToggleTheme: () -> Unit,
    onSearchClicked: () -> Unit,
) {

    val density = LocalDensity.current
    val windowInfo = androidx.compose.ui.platform.LocalWindowInfo.current
    
    // Get screen width in pixels and convert to dp
    val screenWidthPx = windowInfo.containerSize.width
    val screenWidth = with(density) { screenWidthPx.toDp() }

    val animStart = (75 - screenWidth.value).dp

    val animEnd = 0.dp
    val time = 500
    val pos = animateDpAsState(
        targetValue = if (startAnimation) animStart else animEnd,
        animationSpec = tween(
            durationMillis = time
        )
    )
    val alpha = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1f,
        animationSpec = tween(
            durationMillis = time - 100
        )
    )

    val statusBarPadding = getStatusBarPadding()
    
    TopAppBar(
        modifier = Modifier.height(56.dp + statusBarPadding),
        title = {
            Text(
                text = "Tv Shows",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .alpha(alpha = alpha.value)
                    .padding(top = statusBarPadding)
            )
        },
        actions = {
            IconButton(
                onClick = onSearchClicked,
                modifier = Modifier
                    .padding(top = statusBarPadding)
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
                    .padding(top = statusBarPadding)
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
    keyboardState: Boolean,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onCloseClicked: () -> Unit,
    onToggleTheme: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val statusBarPadding = getStatusBarPadding()

    LaunchedEffect(Unit) {
        if (keyboardState) {
            delay(10)
            keyboardController?.show()
            focusRequester.requestFocus()
        }

    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + statusBarPadding),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = statusBarPadding)
        ) {
            TextField(
                modifier = Modifier
                    .weight(.8f)
                    .focusRequester(focusRequester),
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
                        keyboardController?.hide()
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


