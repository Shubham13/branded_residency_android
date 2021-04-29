package com.paragon.sensonic.helpers;

import android.util.Log;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;

public class AmplifyDetails {

    public void getCredential() {
        Amplify.Auth.fetchAuthSession(result -> {
                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                    switch (cognitoAuthSession.getIdentityId().getType()) {
                        case SUCCESS:
                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
                            Log.i("AuthQuickStart", "AccessId: " + cognitoAuthSession.getAWSCredentials().getValue().getAWSAccessKeyId());
                            Log.i("AuthQuickStart", "SecreteId: " + cognitoAuthSession.getAWSCredentials().getValue().getAWSSecretKey());
                            Log.i("AuthQuickStart", "SessionTokenId: " + cognitoAuthSession.getAWSCredentials());
                            break;
                        case FAILURE:
                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                    }
                },
                error -> Log.e("AuthQuickStart", error.toString())
        );

        CognitoCredentialsProvider credentialsProvider =
                new CognitoCredentialsProvider("ap-south-1:9a6afa48-d1db-4d41-9601-d6b2523032eb",
                        Regions.AP_SOUTH_1);

        String token = credentialsProvider.getCredentials().getSessionToken();
    }

}
