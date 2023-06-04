package baeza.guillermo.gymandyang.diets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import baeza.guillermo.gymandyang.R.drawable.checkmark
import baeza.guillermo.gymandyang.ui.theme.MainGreen
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun DietsScreen(navCon: NavHostController, scaffoldState: ScaffoldState, dietsViewModel: DietsViewModel) {
    val loading:Boolean by dietsViewModel.loading.observeAsState(initial = false)
    val completed:Boolean by dietsViewModel.completed.observeAsState(initial = false)
    
    LaunchedEffect(true) {
        dietsViewModel.initUser()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                if (loading) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                }
                else if (completed) {
                    CompletedView(dietsViewModel)
                }
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomSpacer(10)

                        CustomTitle(text = "Diets")

                        CustomSpacer(10)

                        InfoCard()

                        CustomSpacer(20)

                        Button(
                            onClick = { dietsViewModel.setCompleted(true) },
                            modifier = Modifier.fillMaxWidth(0.6f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MainGreen)
                        ) {
                            Text(text = "Send", color = Color.White)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun InfoCard() {
    Card(modifier = Modifier
        .fillMaxWidth(0.9f)
        .height(90.dp), elevation = 10.dp) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info Icon", tint = MainPruple)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Text(
                    text = "Send this data form and a staff member will contact you with a personalized diet.",
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
fun CustomTitle(text: String) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = MainPruple,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun CompletedView(dietsViewModel: DietsViewModel) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(true) {
        withContext(Dispatchers.Default) {
            repeat(51) { // Assuming 100 steps for progress
                delay(50) // Delay between each step
                progress = it / 50f
            }
        }
        dietsViewModel.setCompleted(false)
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = checkmark),
            contentDescription = "Checkmark Icon",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Diet sent correctly",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        LinearProgressIndicator(progress = progress, modifier = Modifier.width(200.dp))
    }
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}