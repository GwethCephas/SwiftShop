package com.ceph.swiftshop.presentation.screens.shop.checkout

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import co.paystack.android.Paystack.TransactionCallback
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge

class PaymentViewModel: ViewModel() {

    fun makePayment(
        context: Context,
        amount: Int,
        cardNumber: String,
        expiryMonth: Int,
        expiryYear: Int,
        cvv: String
    ) {
        PaystackSdk.setPublicKey("pk_test_e12778e3aef5a2e0248551205f385b58e086527d")

        try {
            val card = Card(cardNumber, expiryMonth, expiryYear, cvv)

            if (!card.isValid) {
                Toast.makeText(context, "Card is invalid", Toast.LENGTH_LONG).show()
                return
            }
            val charge = Charge()
            charge.card = card
            charge.amount = amount * 100
            charge.email = "example@gmail.com"
            charge.currency = "KES"

            PaystackSdk.chargeCard(context as Activity, charge, object : TransactionCallback{

                override fun onSuccess(transaction: Transaction?) {
                    Toast.makeText(context, "Payment Successful: ${transaction?.reference}", Toast.LENGTH_LONG).show()
                }

                override fun beforeValidate(transaction: Transaction?) {
                    TODO("Not yet implemented")
                }

                override fun onError(error: Throwable?, transaction: Transaction?) {
                    Toast.makeText(context, "Payment Failed: ${error?.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })


        }catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}