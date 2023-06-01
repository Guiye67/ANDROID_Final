package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DrawerItem(
    title: String,
    icon: Painter,
    description: String,
    scaffoldState: ScaffoldState,
    onRowClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRowClicked()
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = icon, contentDescription = description, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title)
    }
}