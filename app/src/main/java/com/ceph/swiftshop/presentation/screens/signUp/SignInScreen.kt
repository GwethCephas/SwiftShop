package com.ceph.swiftshop.presentation.screens.signUp


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ceph.swiftshop.presentation.authentication.GoogleAuthClient
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


@Composable
fun SignInScreen(
    googleAuthClient: GoogleAuthClient,
    navController: NavHostController
) {


    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthClient.getCurrentUser() != null) {
            navController.navigate(Routes.Home.route)
        }
    }



    Box(
        modifier = Modifier.fillMaxSize().background(color = secondaryLight),
        contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Button(
                onClick = {
                    scope.launch {
                        googleAuthClient.signIn()
                        delay(2000)
                        if (googleAuthClient.getCurrentUser() != null) {
                            navController.navigate(Routes.Home.route)
                        }

                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = onPrimaryLight
                )
            ) {
                Text(text = "Google Sign in")
            }
        }


    }
}