package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.R
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun TopBar(onIconClick: () -> Unit) {
    TopAppBar (
        title = {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "GYM&YANG",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth().padding(10.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.gymyang_logo), contentDescription = "GymYang Logo")
                }
            }
        },
        backgroundColor = MainPruple,
        elevation = 123.dp,
        navigationIcon = {
            IconButton(onClick = onIconClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        },
        modifier = Modifier.height(60.dp)
    )
}