package com.bhaveshp750.composeunit2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaveshp750.composeunit2.ui.theme.ComposeUnit2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUnit2Theme {
                DiceRollerApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeUnit2Theme {
        DiceRollerApp()
    }
}

@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    val imageResource = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { context.startActivity(Intent(context, LemonApp::class.java)) }) {
                Text(text = stringResource(R.string.lemon))
            }
            Button(onClick = { context.startActivity(Intent(context, RollAnimationActivity::class.java)) }) {
                Text(text = stringResource(R.string.roll_animation))
            }
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = imageResource), contentDescription = result.toString())
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { result = (1..6).random() }) {
                Text(
                    text = stringResource(R.string.roll),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 6.dp)
                )
            }
        }
    }
}
@Composable
fun RollMyDiceWithAnimation(modifier: Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    val imageResource = when(result) {
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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = result.toString(),
            modifier = Modifier
                .rotate(angle)
        )
        Spacer(modifier = Modifier.height(16.dp))
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
