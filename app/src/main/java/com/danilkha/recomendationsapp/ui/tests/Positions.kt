package com.danilkha.recomendationsapp.ui.tests

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun PositionCheck(){
    var opened by remember { mutableStateOf(false) }

    val desiredSize by remember { mutableStateOf(IntSize(400, 400)) }
    val desiredPosition by remember { mutableStateOf(Offset(500f,500f)) }

    var targetSize by remember { mutableStateOf(IntSize(0,0)) }
    var targetPos by remember { mutableStateOf(Offset(0f,0f)) }

    val transition = updateTransition(opened, "animating")

    val animatedPos = transition.animateOffset(label = "offset", transitionSpec = {
        tween(durationMillis = 300)
    }) {
        if(it) desiredPosition
        else Offset(targetPos.x, targetPos.y)
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
            /*.onGloballyPositioned {
                desiredSize = it.size
            }*/
    ){
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(modifier = Modifier
            .offset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }
            .size(50.dp)
            .background(color = Color.Black)
            .align(Alignment.Center)
            .onGloballyPositioned {
                targetPos = it.positionInRoot()
                targetSize = it.size
            }
            .clickable {
                opened = true
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
        )


        //if(transition.currentState || transition.targetState){
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
                            Log.i("compose", "onDragEnd: $dragY")
                        },
                        onDragCancel = {
                            dragging = false
                            Log.i("compose", "onDragCancel: ")
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
        //}

        Box(modifier = Modifier
            .offset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }
            .size(50.dp)
            .background(color = Color.Black)
            .align(Alignment.Center)
            .onGloballyPositioned {
                targetPos = it.positionInRoot()
                targetSize = it.size
            }
            .clickable {
                opened = true
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
        )
    }
}

@Preview
@Composable
fun PositionCheckPreview(){
    PositionCheck()
}