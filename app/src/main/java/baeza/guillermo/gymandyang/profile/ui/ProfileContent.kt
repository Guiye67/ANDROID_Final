package baeza.guillermo.gymandyang.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainGreen
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import baeza.guillermo.gymandyang.ui.theme.MainRed
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

@Composable
fun ProfileContent(profileViewModel: ProfileViewModel, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    val email:String by profileViewModel.email.observeAsState(initial = "")
    val name:String by profileViewModel.name.observeAsState(initial = "")
    val surname:String by profileViewModel.surname.observeAsState(initial = "")
    val payment:String by profileViewModel.payment.observeAsState(initial = "")
    val classes:List<String> by profileViewModel.classes.observeAsState(initial = listOf())
    val showPaymentDialog:Boolean by profileViewModel.showPaymentDialog.observeAsState(initial = false)

    var dateExpired by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = false) {
        var userPayment: LocalDate = LocalDate.now()
        if (payment != "0") userPayment = LocalDate.parse(payment)

        dateExpired = payment == "0" || userPayment.isBefore(LocalDate.now())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showPaymentDialog) PaymentDialog(profileViewModel, scope, scaffoldState)

        CustomSpacer(10)

        CustomTitle(text = "PROFILE")

        CustomSpacer(20)

        PaymentCard(profileViewModel, dateExpired, payment)

        CustomSpacer(30)

        DataField(title = "Name", value = name)

        CustomSpacer(20)

        DataField(title = "Surname", value = surname)

        CustomSpacer(20)

        DataField(title = "Email", value = email)

        CustomSpacer(20)

        ClassesColumn(classes)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { profileViewModel.setUpdating(true) },
                backgroundColor = MainPruple
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon", tint = Color.White)
            }
        }
    }
}

@Composable
fun ClassesColumn(classes: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        Text(text = "Classes")
        Divider(modifier = Modifier.width(100.dp), color = MainPruple, thickness = 2.dp)
    }
    if (classes.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(text = "You are not enrolled in any class yet", modifier = Modifier.padding(start = 10.dp))
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.9f),
            contentPadding = PaddingValues(start = 20.dp)
        ) {
            items(classes.size) { index ->
                Text(text = "· ${classes[index]}")
            }
        }
    }
}

@Composable
fun DataField(title: String, value: String) {
    var textSize by remember { mutableStateOf(28.sp) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        Text(text = title)
        Divider(modifier = Modifier.width(100.dp), color = MainPruple, thickness = 2.dp)
        Text(
            text = value,
            fontSize = textSize,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

                if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                    textSize = textSize.times(0.9f)
                }
            },
        )
    }
}

@Composable
fun PaymentCard(profileViewModel: ProfileViewModel, dateExpired: Boolean, payment: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Subscription",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 35.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp),
            backgroundColor = if (dateExpired) { MainRed }
            else { MainGreen },
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (payment == "0") {
                        "Begin your subscription now!"
                    } else if (dateExpired) {
                        "Expired on $payment"
                    } else {
                        "Expires on $payment"
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = { profileViewModel.setShowPaymentDialog(true) }, shape = RoundedCornerShape(20.dp)) {
                        Text(text = if (payment == "0") { "Start" } else { "Extend" })
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaymentDialog(profileViewModel: ProfileViewModel, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(1, 3, 6, 12)
    var selectedOption by remember { mutableStateOf(options[0]) }

    Dialog(
        onDismissRequest ={
            profileViewModel.setShowPaymentDialog(false)
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .height(250.dp)
                .width(320.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Subscription Payment", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Months to extend subscription:", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(5.dp))

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                TextField(
                    value = if (selectedOption == 1) "$selectedOption month" else "$selectedOption months",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                                selectedOption = it
                            }
                        ) {
                            Text(text = if (it == 1) "$it month" else "$it months")
                        }
                    }
                }
            }

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), verticalAlignment = Alignment.Bottom) {
                Box(modifier = Modifier.fillMaxWidth(0.5f), contentAlignment = Alignment.CenterStart) {
                    Button(
                        onClick = { profileViewModel.setShowPaymentDialog(false) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MainRed),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Button(
                        onClick = {
                            profileViewModel.setShowPaymentDialog(false)
                            profileViewModel.addMonths(selectedOption.toLong(), scope, scaffoldState)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MainGreen),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Proceed", color = Color.White)
                    }
                }
            }
        }
    }
}
