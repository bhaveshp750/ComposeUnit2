package com.bhaveshp750.composeunit2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaveshp750.composeunit2.ui.theme.ComposeUnit2Theme

class LemonApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUnit2Theme {
                // A surface container using the 'background' color from the theme
                LemonMainLayout()

            }
        }
    }
}

@Composable
fun LemonMainLayout() {
    var currentStep by remember { mutableStateOf(1) }
    val imageResource: Int
    val stringValue: String
    when(currentStep) {
        1 -> {
            imageResource = R.drawable.lemon_tree
            stringValue = stringResource(R.string.lemon_select)
        }
        2 -> {
            imageResource = R.drawable.lemon_squeeze
            stringValue = stringResource(R.string.lemon_squeeze)
        }
        3 -> {
            imageResource = R.drawable.lemon_drink
            stringValue = stringResource(R.string.lemon_drink)
        }
        else -> {
            imageResource = R.drawable.lemon_restart
            stringValue = stringResource(R.string.lemon_restart)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = stringValue, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            var randomClicks = 0
            var countClick = -1
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = stringValue,
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        BorderStroke(2.dp, Color(105, 205, 216)),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                    .clickable {
                        if(currentStep < 4) {
                            if(currentStep == 2 && countClick < randomClicks){
                                if(randomClicks == 0) randomClicks = (1..4).random()
                                countClick++
                            }else {
                                ++currentStep
                            }
                        } else {
                            currentStep = 1
                        }
                    }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ComposeUnit2Theme {
        LemonMainLayout()
    }
}