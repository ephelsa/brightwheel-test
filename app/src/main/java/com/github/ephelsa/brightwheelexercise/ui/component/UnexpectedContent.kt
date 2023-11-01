package com.github.ephelsa.brightwheelexercise.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.ephelsa.brightwheelexercise.utils.VoidCallback

@Composable
fun UnexpectedContent(
    modifier: Modifier = Modifier,
    icon: @Composable VoidCallback,
    text: @Composable VoidCallback?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()
        text?.invoke()
    }
}