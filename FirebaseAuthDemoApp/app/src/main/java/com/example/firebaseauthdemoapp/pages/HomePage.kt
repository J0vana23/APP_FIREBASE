package com.example.firebaseauthdemoapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauthdemoapp.AuthState
import com.example.firebaseauthdemoapp.AuthViewModel
import com.example.firebaseauthdemoapp.R
import com.example.firebaseauthdemoapp.ui.theme.Link
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    // Recupera o usuário autenticado do Firebase
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email
    val userName = email?.substringBefore('@') ?: "Usuário"

    // Navegação caso o usuário não esteja autenticado
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Texto "Home Page"
        Text(
            text = "Home Page",
            fontSize = 36.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp) // Adiciona um pouco de espaçamento no topo
        )

        // Imagem do topo
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Logo",
            modifier = Modifier
                .clip(CircleShape)
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Texto com o nome do usuário (parte antes do @)
        Text(text = "Bem-vindo, $userName!", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Logout
        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(
                text = "Sign out",
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                color = Link)
        }
    }
}
