package com.danilkha.recomendationsapp.ui.tests

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.danilkha.recomendationsapp.ui.theme.Shapes
import kotlin.math.roundToInt


@Composable
fun TipsCarousel(
    tips: List<Color> = listOf(
        Color.Blue,
        Color.Black,
        Color.Gray,
        Color.Green,
        Color.Red,
        Color.Yellow,
        Color.LightGray
    ),
) {
    var opened by remember { mutableStateOf(false) }

    var targetSize by remember { mutableStateOf(IntSize(0, 0)) }
    var targetPos by remember { mutableStateOf(Offset(0f, 0f)) }

    var desiredSize by remember { mutableStateOf(IntSize(0, 0)) }

    val transition = updateTransition(opened, "animating")

    val animatedPos = transition.animateOffset(label = "offset", transitionSpec = {
        tween(durationMillis = 300)
    }) {
        if(it) Offset(0f, 0f)
        else targetPos
    }

    val animatedSize = transition.animateIntSize(label = "size", transitionSpec = {
        tween(durationMillis = 300)
    }) {
        if(it) desiredSize
        else targetSize
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .onGloballyPositioned {
                desiredSize = it.size
            }
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentPadding = PaddingValues(12.dp, 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(tips, key = { id, color ->
                id
            }) { index, item ->
                var tipSize by remember { mutableStateOf(IntSize(0, 0)) }
                var tipPos by remember { mutableStateOf(Offset(0f, 0f)) }

                Tip(
                    modifier = Modifier
                        .clickable {
                            targetPos = tipPos
                            targetSize = tipSize
                            opened = true
                        }.onGloballyPositioned {
                            tipSize = it.size
                            tipPos = it.positionInRoot()
                        },
                    color = item
                )
            }
        }

        if(transition.currentState || transition.targetState){
            var dragY by remember { mutableStateOf(0f) }
            var dragging by remember { mutableStateOf(false) }

            val viscosity = 0.8f
            val closeThreshold = 300

            LaunchedEffect(dragging){
                if(!dragging){
                    animate(dragY, 0f, block = { value, velocity ->
                        dragY = value
                    })
                }
            }

            Box(Modifier
                .size(with(LocalDensity.current){
                    DpSize(animatedSize.value.width.toDp(), animatedSize.value.height.toDp())
                })
                .offset {
                    IntOffset( animatedPos.value.x.roundToInt(), animatedPos.value.y.roundToInt())
                }
                .absoluteOffset{
                    IntOffset(0, dragY.toInt())
                }
                .align(Alignment.TopStart)
                .background(Color.Magenta)
                .pointerInput(Unit){
                    detectVerticalDragGestures(
                        onDragStart = {
                            dragging = true
                        },
                        onDragEnd = {
                            dragging = false
                            if(dragY > closeThreshold){
                                opened = false
                            }
                        },
                        onDragCancel = {
                            dragging = false
                        },
                        onVerticalDrag = { change: PointerInputChange, amount: Float ->
                            change.consume()
                            if(amount > 0f){
                                dragY += amount * viscosity
                            }
                        }
                    )
                }
            )
        }
    }

}

@Composable
fun Tip(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .background(color, shape = Shapes.medium)
            .width(120.dp)
            .fillMaxHeight()
    ) {

    }
}