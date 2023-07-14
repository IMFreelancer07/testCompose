package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var sizeState by remember {
                mutableStateOf(200.dp)
            }
            val size by animateDpAsState(targetValue = sizeState,

                tween(
                    durationMillis = 1000
                )
            )

            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = Color.Red,
                targetValue = Color.Green,
                animationSpec = infiniteRepeatable(
                    tween(
                        durationMillis = 2000
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box (modifier = Modifier
                .size(size)
                .background(color),
                contentAlignment = Alignment.Center) {
                Button(onClick = {
                    sizeState += 50.dp
                }) {
                    Text(text = "Box Size ++")
                }
            }

        }
    }
}

/** Constraint Layout **/
/**
 * setContent {
val constraints = ConstraintSet {
val greenBox = createRefFor("greenbox")
val blueBox = createRefFor("bluebox")
val guideline = createGuidelineFromTop(0.5f)

constrain(greenBox) {
top.linkTo(guideline)
start.linkTo(parent.start)
width = Dimension.value(100.dp)
height = Dimension.value(100.dp)

}

constrain(blueBox) {
top.linkTo(parent.top)
start.linkTo(greenBox.end)
end.linkTo(parent.end)
width = Dimension.value(100.dp)
height = Dimension.value(100.dp)

}
createHorizontalChain(greenBox,blueBox, chainStyle = ChainStyle.Packed)
}

ConstraintLayout(constraints,modifier = Modifier.fillMaxSize()) {
Box(
modifier = Modifier
.background(Color.Green)
.layoutId("greenbox")
)

Box(
modifier = Modifier
.background(Color.Blue)
.layoutId("bluebox")
)
}

}
 **/