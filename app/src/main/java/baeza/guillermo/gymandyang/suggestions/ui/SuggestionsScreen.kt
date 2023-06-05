package baeza.guillermo.gymandyang.suggestions.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.R
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainGreen
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SuggestionsScreen(scaffoldState: ScaffoldState, suggestionsViewModel: SuggestionsViewModel) {
    val title:String by suggestionsViewModel.title.observeAsState(initial = "")
    val description:String by suggestionsViewModel.description.observeAsState(initial = "")

    val loading:Boolean by suggestionsViewModel.loading.observeAsState(initial = false)
    val completed:Boolean by suggestionsViewModel.completed.observeAsState(initial = false)
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        suggestionsViewModel.initUser()
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
                    CompletedView(suggestionsViewModel)
                }
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomSpacer(10)

                        CustomTitle(text = "Suggestions")

                        CustomSpacer(10)

                        InfoCard()

                        CustomSpacer(20)

                        TitleField(value = title) {
                            suggestionsViewModel.onFieldChange(it, description)
                        }

                        CustomSpacer(15)

                        DescriptionField(value = description) {
                            suggestionsViewModel.onFieldChange(title, it)
                        }

                        CustomSpacer(20)

                        Button(
                            onClick = { suggestionsViewModel.onBtnClick(scope, scaffoldState) },
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .height(45.dp),
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
fun TitleField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text("Title") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(
                3.dp,
                DarkPruple,
                RoundedCornerShape(35.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun DescriptionField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text("Description") },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(380.dp)
            .border(
                4.dp,
                DarkPruple,
                RoundedCornerShape(35.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CompletedView(suggestionsViewModel: SuggestionsViewModel) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(true) {
        withContext(Dispatchers.Default) {
            repeat(51) { // Assuming 100 steps for progress
                delay(50) // Delay between each step
                progress = it / 50f
            }
        }
        suggestionsViewModel.setCompleted(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.checkmark),
            contentDescription = "Checkmark Icon",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Suggestion sent correctly",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        LinearProgressIndicator(progress = progress, modifier = Modifier.width(200.dp))
    }
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
                    text = "Make suggestions to our staff about things you would like to change or improve at the gym.",
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
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}