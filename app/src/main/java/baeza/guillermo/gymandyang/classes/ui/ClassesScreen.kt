package baeza.guillermo.gymandyang.classes.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.ui.models.GymClass
import baeza.guillermo.gymandyang.ui.theme.*

@Composable
fun ClassesScreen(scaffoldState: ScaffoldState, classesViewModel: ClassesViewModel) {
    val classes:List<GymClass> by classesViewModel.classes.observeAsState(initial = listOf())

    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        classesViewModel.initUser()
        classesViewModel.getClasses()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundGray)
                    .padding(5.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomSpacer(10)
                    Text(
                        text = "Classes",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MainPruple,
                        textDecoration = TextDecoration.Underline
                    )
                    CustomSpacer(10)

                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search class") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                        },
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
                        singleLine = true
                    )

                    CustomSpacer(10)

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            classes.forEach { gymClass ->
                                if (searchText.isEmpty()
                                    || gymClass.name.lowercase().contains(searchText.lowercase())
                                ) {
                                    ClassCard(gymClass, scaffoldState, classesViewModel)
                                    CustomSpacer(5)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClassCard(gymClass: GymClass, scaffoldState: ScaffoldState, classesViewModel: ClassesViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        elevation = 10.dp,
        onClick = { expanded = !expanded }
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            FirstCardPart(gymClass)

            if (expanded) SecondCardPart(gymClass, scaffoldState, classesViewModel)
        }
    }
}

@Composable
fun FirstCardPart(gymClass: GymClass) {
    var textSize by remember { mutableStateOf(22.sp) }
    val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri")

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .fillMaxHeight()
                .padding(end = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Name", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = gymClass.name,
                fontSize = textSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

                    if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                        textSize = textSize.times(0.9f)
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyRow {
                item {
                    weekDays.forEach {
                        Card(
                            modifier = Modifier
                                .width(43.dp)
                                .height(35.dp),
                            shape = RoundedCornerShape(20.dp),
                            elevation = 0.dp,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(if (gymClass.days.contains(it)) MainPruple else Color.LightGray)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center) {
                                Text(
                                    text = it.substring(0, 1),
                                    color = if (gymClass.days.contains(it)) Color.White else Color.DarkGray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "Places", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = "${10-gymClass.signedUp.size}/10",
                fontSize = 18.sp,
                maxLines = 1,
                color = if (gymClass.signedUp.size == 10) MainRed else Color.Black
            )
        }
    }
}

@Composable
fun SecondCardPart(gymClass: GymClass, scaffoldState: ScaffoldState, classesViewModel: ClassesViewModel) {
    val userClasses: List<String> by classesViewModel.userClasses.observeAsState(initial = listOf())
    val loading:Boolean by classesViewModel.loading.observeAsState(initial = false)

    val clientIsSignedUp = userClasses.contains(gymClass.name)
    val classIsFull = gymClass.signedUp.size >= 10

    val scope = rememberCoroutineScope()

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Start hour", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = gymClass.hour,
                    fontSize = 20.sp,
                    maxLines = 1
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "Duration", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = gymClass.duration,
                    fontSize = 20.sp,
                    maxLines = 1
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    if (clientIsSignedUp)
                        classesViewModel.singOut(scope, scaffoldState, gymClass.name)
                    else if (!classIsFull) {
                        classesViewModel.signUp(scope, scaffoldState, gymClass.name)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (classIsFull || clientIsSignedUp) MainRed else MainGreen
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                if (loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    Text(
                        text = if (clientIsSignedUp) "Sign Out"
                        else if (!classIsFull) "Sign In"
                        else "Class is full",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}