package baeza.guillermo.gymandyang.diets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import baeza.guillermo.gymandyang.R.drawable.checkmark
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainGreen
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun DietsScreen(scaffoldState: ScaffoldState, dietsViewModel: DietsViewModel) {
    val age:String by dietsViewModel.age.observeAsState(initial = "")
    val gender:String by dietsViewModel.gender.observeAsState(initial = "")
    val weight:String by dietsViewModel.weight.observeAsState(initial = "")
    val height:String by dietsViewModel.height.observeAsState(initial = "")
    val objective:String by dietsViewModel.objective.observeAsState(initial = "")
    val allergens:String by dietsViewModel.allergens.observeAsState(initial = "")

    val loading:Boolean by dietsViewModel.loading.observeAsState(initial = false)
    val completed:Boolean by dietsViewModel.completed.observeAsState(initial = false)
    val scope = rememberCoroutineScope()
    
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
                        
                        SelectMenu("Age*", listOf("10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-99"), age) {
                            dietsViewModel.setAge(it)
                        }

                        CustomSpacer(15)

                        SelectMenu("Gender*", listOf("Male", "Female", "Other"), gender) {
                            dietsViewModel.setGender(it)
                        }
                        
                        CustomSpacer(15)

                        CustomField("Weight*", "number", weight) {
                            dietsViewModel.onFieldChange(it, height, objective, allergens)
                        }

                        CustomSpacer(15)

                        CustomField("Height*", "number", height) {
                            dietsViewModel.onFieldChange(weight, it, objective, allergens)
                        }

                        CustomSpacer(15)

                        CustomField("Objective", "text", objective) {
                            dietsViewModel.onFieldChange(weight, height, it, allergens)
                        }

                        CustomSpacer(15)

                        CustomField("Allergens", "text", allergens) {
                            dietsViewModel.onFieldChange(weight, height, objective, it)
                        }

                        CustomSpacer(20)

                        Button(
                            onClick = { dietsViewModel.onBtnClick(scope, scaffoldState) },
                            modifier = Modifier.fillMaxWidth(0.6f).height(45.dp),
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
fun CustomField(label: String, type: String, value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(label) },
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
        ),
        keyboardOptions = if (type == "number") KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectMenu(label: String, options: List<String>, value: String, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            label = { Text(text = label) },
            placeholder = { Text(text = "Select") },
            modifier = Modifier
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

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onValueChange(it)
                    }
                ) {
                    Text(text = it)
                }
            }
        }
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