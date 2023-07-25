package com.example.testcompose

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val moonScrollSpeed = 0.08f
            val midBgScrollSpeed = 0.03f



            val imageHeight = (LocalConfiguration.current.screenWidthDp * (2f / 3f)).dp
            val lazyListState = rememberLazyListState()

            var moonOffset by remember {
                mutableStateOf(0f)
            }

            var midBgOffset by remember {
                mutableStateOf(0f)
            }

            val nestedScrollCon = object: NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val layoutInfo = lazyListState.layoutInfo
                    // whether first item visible
                    if(lazyListState.firstVisibleItemIndex == 0) {
                        return Offset.Zero
                    }

                    if (layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1) {
                        return Offset.Zero
                    }
                    moonOffset += delta * moonScrollSpeed
                    midBgOffset += delta * midBgScrollSpeed
                    return super.onPreScroll(available, source)
                    return Offset.Zero
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(nestedScrollCon),
                state = lazyListState
            ) {
                items(10) {
                    Text(
                        text = "Sample item", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                item{
                    Box(
                        modifier = Modifier
                            .clipToBounds()
                            .fillMaxWidth()
                            .height(imageHeight + midBgOffset.toDp())
                            .background(Brush.verticalGradient(
                                listOf(
                                    Color(0xFFf36b21),
                                    Color(0xFFf9a521)
                                )
                            ))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_moonbg),
                            contentDescription = "Moon",
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    translationY = moonOffset
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_midbg),
                            contentDescription = "Mid BG",
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    translationY = midBgOffset
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_outerbg),
                            contentDescription = "outer BG",
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                items(20) {
                    Text(
                        text = "Sample item", modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
    private fun Float.toDp(): Dp {
        return (this / Resources.getSystem().displayMetrics.density).dp
    }
}