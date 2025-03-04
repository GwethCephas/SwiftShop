package com.ceph.swiftshop.presentation.authentication

import android.content.Context
import android.util.Log
import androidx.credentials.*
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.ceph.swiftshop.data.utils.Constants.WEB_CLIENT
import com.ceph.swiftshop.domain.model.SignInResult
import com.ceph.swiftshop.domain.model.UserData
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClient(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    suspend fun signIn(): SignInResult {
        return try {

            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(WEB_CLIENT)
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialResponse: GetCredentialResponse = credentialManager.getCredential(context, request)
            val credential = credentialResponse.credential

            Log.d("GoogleAuth", "Credential received: ${credential.data}")

            authenticateWithFirebase(credential)

        } catch (e: NoCredentialException) {
            SignInResult(null, "No credential found")
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            SignInResult(null, e.message)
        }
    }

    private suspend fun authenticateWithFirebase(credential: Credential): SignInResult {
        return try {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            if (googleIdToken.isEmpty()) {
                return SignInResult(null, "No Google ID token")
            }

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val user = auth.signInWithCredential(firebaseCredential).await().user


            SignInResult(
                data = user?.run {
                    UserData(
                        userName = displayName,
                        email = email,
                        photoUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            SignInResult(null, e.message)
        }
    }

    fun getCurrentUser(): UserData? = auth.currentUser?.run {
        UserData(
            userName = displayName,
            email = email,
            photoUrl = photoUrl?.toString()
        )
    }

    suspend fun signOut() {
        val request = ClearCredentialStateRequest()
        try {
            credentialManager.clearCredentialState(request)
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

}
