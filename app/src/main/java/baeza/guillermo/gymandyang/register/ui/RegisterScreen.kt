package baeza.guillermo.gymandyang.register.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.R
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import baeza.guillermo.gymandyang.R.drawable.gymyang_logo
import baeza.guillermo.gymandyang.ui.theme.DarkPruple

@Composable
fun RegisterScreen(navCon: NavHostController, scaffoldState: ScaffoldState, registerViewModel: RegisterViewModel) {
    val email:String by registerViewModel.email.observeAsState(initial = "")
    val name:String by registerViewModel.name.observeAsState(initial = "")
    val surname:String by registerViewModel.surname.observeAsState(initial = "")
    val password:String by registerViewModel.password.observeAsState(initial = "")
    val passwordRepeat:String by registerViewModel.passwordRepeat.observeAsState(initial = "")
    val loading:Boolean by registerViewModel.loading.observeAsState(initial = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MainPruple),
                contentAlignment = Alignment.Center,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(0.9f),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (!loading) {
                            CustomSpacer(15)

                            RegisterImage()

                            CustomSpacer(20)

                            RegisterTitle()

                            CustomSpacer(25)

                            CustomTextField("Email", email) {
                                registerViewModel.onFieldChange(it, name, surname, password, passwordRepeat)
                            }

                            CustomSpacer(10)

                            CustomTextField("Name", name) {
                                registerViewModel.onFieldChange(email, it, surname, password, passwordRepeat)
                            }

                            CustomSpacer(10)

                            CustomTextField("Surname", surname) {
                                registerViewModel.onFieldChange(email, name, it, password, passwordRepeat)
                            }

                            CustomSpacer(10)

                            PasswordField("Password", password) {
                                registerViewModel.onFieldChange(email, name, surname, it, passwordRepeat)
                            }

                            CustomSpacer(10)

                            PasswordField("Repeat Password", passwordRepeat) {
                                registerViewModel.onFieldChange(email, name, surname, password, it)
                            }

                            CustomSpacer(20)

                            RegisterButton {
                                registerViewModel.onBtnClick(navCon, scope, scaffoldState)
                            }
                        } else {
                            CircularProgressIndicator(strokeWidth = 3.dp)
                        }

                    }
                }
            }
        }
    )
}

@Composable
fun RegisterButton(onBtnClick: () -> Unit) {
    Button(
        onClick = { onBtnClick() },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(45.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "Register")
    }
}

@Composable
fun CustomTextField(label: String, value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.9f)
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
fun PasswordField(label: String, value: String, onValueChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation =
            if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = {passwordVisible = !passwordVisible}){
                if (passwordVisible) {
                    Icon(
                        painter = painterResource(id = R.drawable.eye_crossed),
                        contentDescription = "Crossed Eye",
                        tint = MainPruple
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = "Eye",
                        tint = MainPruple
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .border(
                3.dp,
                MainPruple,
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
fun RegisterImage() {
    Image(
        painter = painterResource(id = gymyang_logo),
        contentDescription = "Gym&Yang Logo",
        modifier = Modifier
            .border(5.dp, MainPruple, CircleShape)
            .size(170.dp)
    )
}

@Composable
fun RegisterTitle() {
    Text(
        text = "Join GYM&YANG Today!",
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}
