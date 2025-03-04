package com.ceph.swiftshop.presentation.screens.shop.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ceph.swiftshop.R
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@Composable
fun PaymentCompleteScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(onPrimaryLight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.shopping),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = "Success!",
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp
            )
            Text(
                text = "Your order will be delivered soon",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Text(
                text = "Thank you for shopping with us",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Button(
                onClick = { navController.navigate(Routes.Home.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryLight,
                    contentColor = onPrimaryLight
                )
            ) {
                Text(text = "Continue Shopping")
            }
        }
    }
}