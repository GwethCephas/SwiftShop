package com.ceph.swiftshop.presentation.screens.shop.checkout

import android.app.NotificationManager
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import com.ceph.swiftshop.presentation.navigation.Routes
import com.ceph.swiftshop.ui.theme.onPrimaryLight
import com.ceph.swiftshop.ui.theme.secondaryLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    paddingValues: PaddingValues,
    totalItems: Int,
    totalPrice: Double,
    navController: NavHostController,
    viewModel: PaymentViewModel
) {
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { navController.navigate(Routes.Cart.route) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }, title = { Text(text = "Check out") }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.outlineVariant)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Order summary",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Items")
                    Text(text = "$totalItems")

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "SubTotal")
                    Text(text = "KES ${"%.2f".format(totalPrice)}")


                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Delivery Charges")
                    Text(text = "KES 50")


                }
                HorizontalDivider(color = Color.White)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total")
                    Text(text = "KES ${"%.2f".format(totalPrice + 50)}")

                }

            }
        }

        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                isBottomSheetOpen = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = secondaryLight, contentColor = onPrimaryLight
            )
        ) {
            Text(text = "Confirm   Order")
        }

    }
    if (isBottomSheetOpen) {
        ModalDrawerPayment(
            viewModel = viewModel,
            paddingValues = paddingValues,
            totalPrice = totalPrice + 50,
            onDismissRequest = {
                isBottomSheetOpen = false
            },
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawerPayment(
    viewModel: PaymentViewModel,
    paddingValues: PaddingValues,
    totalPrice: Double,
    onDismissRequest: () -> Unit,
    navController: NavHostController
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    var cardDetails by remember { mutableStateOf(CardDetails()) }

    val notification = NotificationCompat.Builder(context, "PAYMENT_CHANNEL")
        .setContentTitle("SwiftShop Payment")
        .setContentText("Your payment of KES ${"%.2f".format(totalPrice)} was successful!")
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .build()


    val notificationManager =
        context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardDetailsTextField(
                    value = cardDetails.cardNumber,
                    onValueChange = {
                        cardDetails = cardDetails.copy(cardNumber = it)
                    },
                    label = "Card Number",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CardDetailsTextField(
                    value = cardDetails.expiryMonth,
                    onValueChange = {
                        if (it.length <= 2 && it.all { char -> char.isDigit() })
                            cardDetails = cardDetails.copy(
                                expiryMonth = it

                            )

                    },
                    label = "Expiry Month",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CardDetailsTextField(
                    value = cardDetails.expiryYear,
                    onValueChange = {
                        if (it.length <= 2 && it.all { char -> char.isDigit() }) {
                            cardDetails = cardDetails.copy(
                                expiryYear = it
                            )
                        }
                    },
                    label = "Expiry Year",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CardDetailsTextField(
                    value = cardDetails.cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                            cardDetails = cardDetails.copy(
                                cvv = it
                            )
                        }
                    },
                    label = "CVV",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Text(
                    text = "Total: ${"%.2f".format(totalPrice)}",
                    modifier = Modifier.padding(20.dp)
                )


                Button(
                    onClick = {
                        val monthInt = cardDetails.expiryMonth.toIntOrNull() ?: 0
                        val yearInt = cardDetails.expiryYear.toIntOrNull() ?: 0

                        if (monthInt in 1..12 && yearInt in 0..99) {
                            viewModel.makePayment(
                                context = context,
                                amount = totalPrice.toInt(),
                                cardNumber = cardDetails.cardNumber,
                                expiryMonth = monthInt,
                                expiryYear = 2000 + yearInt, // Convert YY to YYYY format
                                cvv = cardDetails.cvv
                            )
                            notificationManager.notify(1, notification)
                            navController.navigate("PaymentSuccess")
                            onDismissRequest()

                        } else {
                            Toast.makeText(context, "Invalid expiry date", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondaryLight, contentColor = onPrimaryLight
                    )
                ) {
                    Text("Pay Now")
                }
            }
        }

    }
}


@Composable
fun CardDetailsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = TextFieldDefaults.colors(
            cursorColor = secondaryLight
        )
    )
}

data class CardDetails(
    val cardNumber: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val cvv: String = "",
    val amount: String = ""
)