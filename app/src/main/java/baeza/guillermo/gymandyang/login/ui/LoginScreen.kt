package baeza.guillermo.gymandyang.login.ui

import androidx.compose.foundation.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.R.drawable.*
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun LoginScreen(navCon: NavHostController, scaffoldState: ScaffoldState, loginViewModel: LoginViewModel) {
    val email:String by loginViewModel.email.observeAsState(initial = "")
    val password:String by loginViewModel.password.observeAsState(initial = "")
    val loading:Boolean by loginViewModel.loading.observeAsState(false)
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainPruple),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.65f),
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (!loading) {
                            LogoImage()

                            CustomSpacer(10)

                            LoginTitle()

                            CustomSpacer(20)

                            EmailField(value = email) { loginViewModel.onFieldChange(it, password) }

                            CustomSpacer(15)

                            PasswordField(password = password) { loginViewModel.onFieldChange(email, it) }

                            CustomSpacer(20)

                            LoginButton { loginViewModel.onBtnClick(navCon, scope, scaffoldState) }

                            CustomSpacer(10)

                            RegisterLink(navCon) { loginViewModel.onFieldChange("", "") }
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
fun LoginButton(onBtnClick: () -> Unit) {
    Button(
        onClick = { onBtnClick() },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(45.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "Login")
    }
}

@Composable
fun EmailField(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text("Email") },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = email_icon),
                contentDescription = "Email",
                tint = MainPruple
            )
        },
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
fun PasswordField(password: String, onValueChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onValueChanged(it) },
        label = { Text("Password") },
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
                        painter = painterResource(id = eye_crossed),
                        contentDescription = "Crossed Eye",
                        tint = MainPruple
                    )
                } else {
                    Icon(
                        painter = painterResource(id = eye),
                        contentDescription = "Eye",
                        tint = MainPruple
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = lock),
                contentDescription = "Password",
                tint = MainPruple
            )
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
fun LoginTitle() {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "GYM&YANG",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )
        Canvas(
            Modifier
                .fillMaxWidth(0.65f)
                .height(60.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawLine( //top left line
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = 50f, y = 0f),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //top right line
                start = Offset(x = canvasWidth-50f, y = 0f),
                end = Offset(x = canvasWidth, y = 0f),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //bottom left line
                start = Offset(x = 0f, y = canvasHeight),
                end = Offset(x = 50f, y = canvasHeight),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //bottom right line
                start = Offset(x = canvasWidth-50f, y = canvasHeight),
                end = Offset(x = canvasWidth, y = canvasHeight),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //left top line
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = 0f, y = 50f),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //left bottom line
                start = Offset(x = 0f, y = canvasHeight-50f),
                end = Offset(x = 0f, y = canvasHeight),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //right top line
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = canvasWidth, y = 50f),
                strokeWidth = 5f,
                color = DarkPruple
            )
            drawLine( //right bottom line
                start = Offset(x = canvasWidth, y = canvasHeight-50f),
                end = Offset(x = canvasWidth, y = canvasHeight),
                strokeWidth = 5f,
                color = DarkPruple
            )
        }
    }
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(id = gymyang_logo),
        contentDescription = "Gym&Yang Logo",
        modifier = Modifier.border(5.dp, MainPruple, CircleShape)
    )
}

@Composable
fun RegisterLink(navCon: NavHostController, onTextClick: () -> Unit) {
    Text(
        text = "Create new account?",
        modifier = Modifier
            .clickable {
                onTextClick()
                navCon.navigate(Routes.RegisterScreen.route)
            },
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
        color = MainPruple
    )
}