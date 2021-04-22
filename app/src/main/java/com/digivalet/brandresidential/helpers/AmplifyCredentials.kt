package com.digivalet.brandresidential.helpers

import android.util.Log
import com.amazonaws.auth.CognitoCredentialsProvider
import com.amazonaws.regions.Regions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AmplifyCredentials {

    public fun getCredentials(){
        val credentialsProvider = CognitoCredentialsProvider(
            "ap-south-1:9a6afa48-d1db-4d41-9601-d6b2523032eb",  // Identity Pool ID
            Regions.AP_SOUTH_1, // Region
        )

        GlobalScope.launch {
            var Id : String = credentialsProvider.identityId
            var accessId = credentialsProvider.credentials.awsAccessKeyId
            var secretKey = credentialsProvider.credentials.awsSecretKey
            var token = credentialsProvider.credentials.sessionToken
            Log.e("Aws",""+Id+" "+accessId+" "+secretKey+" "+token)
        }
    }

}