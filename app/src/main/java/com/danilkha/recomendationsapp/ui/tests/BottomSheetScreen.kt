package com.danilkha.recomendationsapp.ui.tests

import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.danilkha.recomendationsapp.ui.utils.findActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScreen() {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current.findActivity()


    val keyboardMode = state.currentValue != ModalBottomSheetValue.Hidden || state.targetValue == ModalBottomSheetValue.Expanded

    LaunchedEffect(keyboardMode){
        if(keyboardMode){
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }else{
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    var imeVisible by remember { mutableStateOf(false) }

    val view = LocalView.current

/*    LaunchedEffect(Unit){
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            Log.i("compose", "insets: $imeVisible")
            insets
        }
    }*/

    LaunchedEffect(imeVisible){
        Log.i("compose", "BottomSheetScreen: $imeVisible")
    }


    ModalBottomSheetLayout(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .fillMaxSize(),
        sheetContent = {
            Screen()
        },
        sheetState = state,
    ){
        var text by remember { mutableStateOf("") }
        Box(modifier = Modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .fillMaxSize()
        ){
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                scope.launch {
                    state.show()
                }
            }){
                Text("show")
            }

            OutlinedTextField(
                modifier = Modifier.align(Alignment.BottomCenter).padding(50.dp),
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background, textColor = MaterialTheme.colors.onBackground)
            )
        }
    }
}