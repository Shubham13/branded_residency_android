package com.paragon.sensonic.auth;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.paragon.sensonic.utils.AppConstant;
import com.paragon.sensonic.utils.AsyncCommand;

public class AWSCredentialsProvider extends AsyncCommand<Credentials> {

    @Override
    public Credentials run() throws Exception {
        CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(AppConstant.IDENTITY_POOL_ID,
                Regions.AP_SOUTH_1);
        
        return new Credentials(credentialsProvider.getCredentials().getAWSAccessKeyId(),
                credentialsProvider.getCredentials().getAWSSecretKey(),
                credentialsProvider.getCredentials().getSessionToken());
    }
}