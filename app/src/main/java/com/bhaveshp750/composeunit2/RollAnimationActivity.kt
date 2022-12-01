package com.bhaveshp750.composeunit2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaveshp750.composeunit2.ui.theme.ComposeUnit2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RollAnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUnit2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DiceRollerAnimateApp()
                }
            }
        }
    }
}

@Composable
fun DiceRollerAnimateApp() {
    AppContent(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun AppContent(modifier: Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    val scope = rememberCoroutineScope()

    val isRotated = rememberSaveable { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (isRotated.value) 360F else 0F,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        )
    )
    val angle2: Float by animateFloatAsState(
        targetValue = if (isRotated.value) 360F else 0F,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearOutSlowInEasing
        )
    )
    val angle3: Float by animateFloatAsState(
        targetValue = if (isRotated.value) 360F else 0F,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        )
    )
    val angle4: Float by animateFloatAsState(
        targetValue = if (isRotated.value) 360F else 0F,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutLinearInEasing
        )
    )


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.weight(1f).wrapContentHeight()) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .rotate(angle)
            )
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .rotate(angle2)
            )
        }
        Row(Modifier.weight(1f).wrapContentHeight()) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .rotate(angle3)
            )
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .rotate(angle4)
            )
        }
        Row(Modifier.weight(1f).wrapContentHeight()) {
            Button(onClick = {
                isRotated.value = !isRotated.value
                scope.launch {
                    delay(200)
                    result = (1..6).random()
                }
            }) {
                Text(
                    text = stringResource(R.string.roll),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    ComposeUnit2Theme {
        DiceRollerAnimateApp()
    }
}