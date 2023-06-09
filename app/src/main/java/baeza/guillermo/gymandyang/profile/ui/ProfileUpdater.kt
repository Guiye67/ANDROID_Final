package baeza.guillermo.gymandyang.profile.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.R
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainGreen
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import baeza.guillermo.gymandyang.ui.theme.MainRed
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProfileUpdater(profileViewModel: ProfileViewModel, navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    val email:String by profileViewModel.email.observeAsState(initial = "")
    val name:String by profileViewModel.name.observeAsState(initial = "")
    val surname:String by profileViewModel.surname.observeAsState(initial = "")
    val newPassword:String by profileViewModel.newPassword.observeAsState(initial = "")
    val newPasswordRepeat:String by profileViewModel.newPasswordRepeat.observeAsState(initial = "")
    val changePassword:Boolean by profileViewModel.changePassword.observeAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTitle(text = "EDITING PROFILE")

        CustomSpacer(20)
        
        CustomTextField(label = "Name", value = name) {
            profileViewModel.onFieldChange(it, surname, email, newPassword, newPasswordRepeat)
        }

        CustomSpacer(10)

        CustomTextField(label = "Surname", value = surname) {
            profileViewModel.onFieldChange(name, it, email, newPassword, newPasswordRepeat)
        }

        CustomSpacer(10)

        CustomTextField(label = "Email", value = email) {
            profileViewModel.onFieldChange(name, surname, it, newPassword, newPasswordRepeat)
        }

        CustomSpacer(10)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = changePassword,
                onCheckedChange = { profileViewModel.setChangePassword(!changePassword) },
                colors = CheckboxDefaults.colors(checkedColor = MainPruple)
            )
            Text(text = "Change password?")
        }

        ShowPasswordFields(
            profileViewModel,
            name, surname, email, newPassword, newPasswordRepeat,
            changePassword
        )

        CustomSpacer(20)

        UpdaterButtons(profileViewModel, navCon, scope, scaffoldState)
    }
}

@Composable
fun ShowPasswordFields(
    profileViewModel: ProfileViewModel,
    name: String,
    surname: String,
    email: String,
    newPassword: String,
    newPasswordRepeat: String,
    changePassword: Boolean
) {
    var colHeight by remember { mutableStateOf(0.dp) }
    var aux by remember { mutableStateOf(0) }

    val boxHeightAnimation by animateDpAsState(
        targetValue = if (!changePassword) 150.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)
    )

    LaunchedEffect(changePassword) {
        if (aux != 0) colHeight = boxHeightAnimation
        aux++
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(colHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSpacer(10)

        PasswordField(label = "New Password", value = newPassword) {
            profileViewModel.onFieldChange(name, surname, email, it, newPasswordRepeat)
        }

        CustomSpacer(10)

        PasswordField(label = "Repeat New Password", value = newPasswordRepeat) {
            profileViewModel.onFieldChange(name, surname, email, newPassword, it)
        }
    }
}

@Composable
fun UpdaterButtons(profileViewModel: ProfileViewModel, navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Row(modifier = Modifier
        .padding(bottom = 20.dp), verticalAlignment = Alignment.Bottom
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(end = 5.dp), contentAlignment = Alignment.CenterStart) {
            Button(onClick = { profileViewModel.onCancelUpdating() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MainRed)
            ) {
                Text(text = "Cancel", color = Color.White)
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp), contentAlignment = Alignment.CenterEnd) {
            Button(onClick = { profileViewModel.onBtnClick(scope, scaffoldState) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MainGreen)
            ) {
                Text(text = "Update", color = Color.White)
            }
        }
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
