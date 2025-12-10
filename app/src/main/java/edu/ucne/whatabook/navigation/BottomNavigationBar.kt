package edu.ucne.whatabook.navigation
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    onHome: () -> Unit,
    onCarrito: () -> Unit,
    onPerfil: () -> Unit,
    currentRoute: Int
) {
    NavigationBar(containerColor = Color.Black) {
        NavigationBarItem(
            selected = currentRoute == 1,
            onClick = onHome,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            },
            label = { Text("Inicio", color = Color.White, fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = currentRoute == 2,
            onClick = onCarrito,
            icon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            },
            label = { Text("Carrito", color = Color.White, fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = currentRoute == 3,
            onClick = onPerfil,
            icon = {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            },
            label = { Text("Perfil", color = Color.White, fontSize = 10.sp) }
        )
    }
}