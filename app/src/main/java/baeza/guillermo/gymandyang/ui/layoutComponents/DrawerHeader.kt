package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun DrawerHeader(name: String, surname: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.22f)
            .background(MainPruple),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "GYM&YANG",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
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
                    color = Color.White
                )
                drawLine( //top right line
                    start = Offset(x = canvasWidth-50f, y = 0f),
                    end = Offset(x = canvasWidth, y = 0f),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //bottom left line
                    start = Offset(x = 0f, y = canvasHeight),
                    end = Offset(x = 50f, y = canvasHeight),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //bottom right line
                    start = Offset(x = canvasWidth-50f, y = canvasHeight),
                    end = Offset(x = canvasWidth, y = canvasHeight),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //left top line
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = 50f),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //left bottom line
                    start = Offset(x = 0f, y = canvasHeight-50f),
                    end = Offset(x = 0f, y = canvasHeight),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //right top line
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = canvasWidth, y = 50f),
                    strokeWidth = 5f,
                    color = Color.White
                )
                drawLine( //right bottom line
                    start = Offset(x = canvasWidth, y = canvasHeight-50f),
                    end = Offset(x = canvasWidth, y = canvasHeight),
                    strokeWidth = 5f,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "WELCOME,",
            fontSize = 22.sp,
            color = Color.White
        )
        Text(
            text = "$name $surname".uppercase(),
            fontSize = 22.sp,
            color = Color.White
        )

    }
}