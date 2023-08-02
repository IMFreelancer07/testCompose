package com.example.testcompose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Int,
    modifier : Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
){
    var oldCount by remember {
        mutableStateOf(count)
    }

    SideEffect {
        oldCount = count
    }

    Row(modifier = modifier) {
        val countStr = count.toString()
        val oldCountStr = oldCount.toString()

        for (i in countStr.indices) {

            val oldChar = oldCountStr.getOrNull(i)
            val newChar = countStr[i]

            val char = if(oldChar == newChar) {
                oldCountStr[i]
            } else {
                countStr[i]
            }

            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}