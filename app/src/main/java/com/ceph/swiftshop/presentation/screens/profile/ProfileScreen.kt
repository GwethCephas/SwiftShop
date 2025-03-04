package com.ceph.swiftshop.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ceph.swiftshop.domain.model.UserData
import com.ceph.swiftshop.presentation.authentication.GoogleAuthClient
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.secondaryLight
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    userData: UserData?,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val googleAuthClient = GoogleAuthClient(context)

    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = userData?.photoUrl,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }
            userData?.userName?.let { userName -> Text(text = userName) }
            userData?.email?.let { email -> Text(text = email) }

        }
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomRows(
                icon = Icons.Default.Person,
                text = "Profile",
                onClick = {

                }
            )
            CustomRows(
                icon = Icons.Default.Settings,
                text = "Settings",
                onClick = {

                    navController.navigate(Routes.Settings.route) {
                        popUpTo(Routes.Profile.route) {
                            inclusive = true
                        }
                    }
                }
            )
            CustomRows(
                icon = Icons.Default.Email,
                text = "Contact",
                onClick = {

                }
            )
            CustomRows(
                icon = Icons.Default.Share,
                text = "Share",
                onClick = {

                }
            )
            CustomRows(
                icon = Icons.Default.Info,
                text = "Help",
                onClick = {

                }
            )
            Spacer(modifier = Modifier.size(30.dp))
            TextButton(
                onClick = {
                    scope.launch {
                        googleAuthClient.signOut()
                        navController.navigate(Routes.SignIn.route)
                    }
                }
            ) {
                Text(
                    text = " Sign Out",
                    color = secondaryLight,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }

}


@Composable
fun CustomRows(
    icon: ImageVector,
    navigationIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    text: String,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = text,
                modifier = Modifier.size(30.dp)
            )
        }
    }

}