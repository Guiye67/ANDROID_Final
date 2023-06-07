package baeza.guillermo.gymandyang.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.models.GymClass
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.ui.theme.BackgroundGray
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun HomeScreen(navCon: NavHostController, scaffoldState: ScaffoldState, homeViewModel: HomeViewModel) {
    val classes:List<GymClass> by homeViewModel.classes.observeAsState(initial = listOf())
    val loading:Boolean by homeViewModel.loading.observeAsState(initial = false)
    val selectedDate:LocalDate by homeViewModel.selectedDate.observeAsState(initial = LocalDate.now())
    val todaysClasses:List<GymClass> by homeViewModel.todaysClasses.observeAsState(initial = listOf())

    LaunchedEffect(true) {
        homeViewModel.getClasses()
        homeViewModel.onDateSelected(LocalDate.now())
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomSpacer(5)
                        Text(
                            text = "Home",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MainPruple,
                            textDecoration = TextDecoration.Underline
                        )

                        SelectableCalendar(
                            today = selectedDate,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            firstDayOfWeek = DayOfWeek.MONDAY,
                            dayContent = {
                                DefaultDay(it, selectionColor = MainPruple, currentDayColor = MainPruple) { date ->
                                    homeViewModel.onDateSelected(date)
                                }
                            }
                        )

                        Text(
                            text =
                            if (selectedDate == LocalDate.now())
                                "Classes for $selectedDate (today):"
                            else
                                "Classes for $selectedDate",
                            textDecoration = TextDecoration.Underline
                        )
                        CustomSpacer(5)

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            if (classes.isEmpty()) {
                                Column {
                                    Text(text = "You have not signed up to any class yet, start whenever you want!")
                                    Button(onClick = { navCon.navigate(Routes.ClassesScreen.route) }) {
                                        Text(text = "Start now!")
                                    }
                                }
                            } else if (selectedDate.dayOfWeek.toString() == "SATURDAY" || selectedDate.dayOfWeek.toString() == "SUNDAY") {
                                Text(text = "No classes on weekends!")
                            } else if (todaysClasses.isEmpty()) {
                                Text(text = "No classes for the day!")
                            } else {
                                LazyColumn (modifier = Modifier.fillMaxSize()) {
                                    item {
                                        todaysClasses.forEach {
                                            ClassCard(it)
                                            CustomSpacer(5)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ClassCard(gymClass: GymClass) {
    var textSize by remember { mutableStateOf(22.sp) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 5.dp
    ) {
        Row (modifier = Modifier.fillMaxWidth().padding(5.dp)) {
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "Name", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = gymClass.name,
                    fontSize = textSize,
                    fontWeight = FontWeight.SemiBold,
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

            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "Hour", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = gymClass.hour,
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

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Duration", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = gymClass.duration,
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
        }
    }
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}

