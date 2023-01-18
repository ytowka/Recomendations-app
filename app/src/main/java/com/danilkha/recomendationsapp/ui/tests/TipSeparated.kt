package com.danilkha.recomendationsapp.ui.tests

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.danilkha.recomendationsapp.ui.theme.Shapes
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun <T> TipCarousel(
    modifier: Modifier = Modifier,
    openedIndex: Int?,
    tipList: List<T>,
    onOpen: (index: Int) -> Unit,
    onClose: () -> Unit,
    tipWidget: @Composable (index: Int, item: T) -> Unit,
    expandedTipContent: @Composable (index: Int, item: T) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(12.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(tipList) { index: Int, item: T ->
            ExpandableWidget(
                expanded = index == openedIndex,
                widgetContent = { tipWidget(index, item) },
                expandedContent = { expandedTipContent(index, item) },
                onOpen = { onOpen(index) },
                onClose = onClose
            )
        }
    }
}

@Composable
fun ExpandableWidget(
    expanded: Boolean,
    widgetContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit,
    onOpen: () -> Unit,
    onClose: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.roundToPx() }
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.roundToPx() }


    var targetSize by remember { mutableStateOf(IntSize(0, 0)) }
    var targetPos by remember { mutableStateOf(Offset(0f, 0f)) }

    val transition = updateTransition(expanded, "animating")

    val animatedPos = transition.animateOffset(label = "offset", transitionSpec = {
        tween(durationMillis = 300)
    }) {
        if(it) Offset(0f, 0f)
        else targetPos
    }

    val animatedDim = transition.animateFloat(label = "dim", transitionSpec ={
        tween(300)
    }){
        if(it) 1f else 0f
    }



    val animatedSize = transition.animateIntSize(label = "size", transitionSpec = {
        tween(durationMillis = 300)
    }) {
        if(it) IntSize(screenWidth, screenHeight)
        else targetSize
    }


    Box(modifier = Modifier
        .clickable {
            onOpen()
        }
        .onGloballyPositioned {
            targetSize = it.size
            targetPos = it.positionInRoot()
        }) {
        widgetContent()
    }


    if(transition.currentState || transition.targetState){
        Popup(
            offset = IntOffset(0,0),
        ){
            var dragY by remember { mutableStateOf(0f) }
            var dragging by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {



                val viscosity = 0.6f
                val closeThreshold = 300

                LaunchedEffect(dragging) {
                    if (!dragging) {
                        animate(dragY, 0f, block = { value, velocity ->
                            dragY = value
                        })
                    }
                }

                Box(Modifier
                    .size(with(LocalDensity.current) {
                        DpSize(
                            animatedSize.value.width.toDp(),
                            animatedSize.value.height.toDp()
                        )
                    })
                    .absoluteOffset {
                        IntOffset(0, dragY.toInt()) + IntOffset(
                            animatedPos.value.x.roundToInt(),
                            animatedPos.value.y.roundToInt()
                        )
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onDragStart = {
                                dragging = true
                            },
                            onDragEnd = {
                                dragging = false
                                if (dragY > closeThreshold) {
                                    onClose()
                                }
                            },
                            onDragCancel = {
                                dragging = false
                            },
                            onVerticalDrag = { change: PointerInputChange, amount: Float ->
                                change.consume()
                                if (amount > 0f || dragY != 0f ) {
                                    dragY += amount * viscosity
                                }
                            }
                        )
                    }
                ) {
                    expandedContent()
                }
            }
        }
    }
}

@Preview
@Composable
fun CarouselPreview(){
    var openedIndex by remember { mutableStateOf<Int?>(null) }

    TipCarousel(
        modifier = Modifier.height(80.dp),
        openedIndex = openedIndex,
        onOpen = { if(openedIndex == null) openedIndex = it },
        onClose = { openedIndex = null },
        tipList = listOf(
            Color.Blue,
            Color.Black,
            Color.Gray,
            Color.Green,
            Color.Red,
            Color.Yellow,
            Color.LightGray
        ),
        tipWidget = { index, item ->
            Box(
                modifier = Modifier
                    .background(item, shape = Shapes.medium)
                    .width(120.dp)
                    .fillMaxHeight()
            )
        },
        expandedTipContent = { index, item ->
            Box(
                modifier = Modifier
                    .background(item)
                    .fillMaxSize()
            )
        }
    )
}