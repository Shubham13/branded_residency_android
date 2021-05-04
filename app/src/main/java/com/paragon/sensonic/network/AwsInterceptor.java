/*
 *  Copyright (C) 2017 Ghedeon
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.paragon.sensonic.network;

import android.util.Log;

import androidx.annotation.Nullable;

import com.amazonaws.DefaultRequest;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.Regions;
import com.paragon.sensonic.utils.AwsUtill;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static com.paragon.sensonic.network.Constant.POOL_ID;
import static com.paragon.sensonic.network.Constant.REGION;
import static com.paragon.sensonic.network.Constant.SERVICE_NAME;


public class AwsInterceptor implements Interceptor {

    private final CognitoCredentialsProvider credentialsProvider;
    
    private final AwsUtill signer;

    private final RequestBody requestBody;

    private final HttpMethodName httpMethodName;

    private final String method;

    private String BASE_URL = "https://api-qa.digivaletliving.com/auth/v1/";

    private MediaType mediaType;

    public AwsInterceptor(HttpMethodName methodType, String method, String body) {
        credentialsProvider = new CognitoCredentialsProvider(POOL_ID, Regions.AP_SOUTH_1);
        mediaType = MediaType.parse("application/json");
        this.httpMethodName = methodType;
        this.requestBody = RequestBody.create(mediaType,body);
        this.method = method;
        signer = new AwsUtill();
        signer.setServiceName(SERVICE_NAME);
        signer.setRegionName(REGION);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request signedRequest = sign(chain.request());
        return chain.proceed(signedRequest);
    }

    
    private Request sign(Request request) throws IOException {
        Request.Builder builder = request.newBuilder();
        DefaultRequest awsDummyRequest = new DefaultRequest(SERVICE_NAME);
        awsDummyRequest.setEndpoint(URI.create(BASE_URL+""+method));
        awsDummyRequest.setHttpMethod(httpMethodName);
        setBody(awsDummyRequest, requestBody);
        String accessId = credentialsProvider.getCredentials().getAWSAccessKeyId().trim();
        String secretKey = credentialsProvider.getCredentials().getAWSSecretKey().trim();
        String token = credentialsProvider.getCredentials().getSessionToken().trim();
        Log.e("AccessId", accessId);
        Log.e("secreteKey", secretKey);
        Log.e("token", token);
        signer.sign(awsDummyRequest, credentialsProvider.getCredentials());
        applyAwsHeaders(builder, awsDummyRequest.getHeaders());
        return builder.build();
    }

    private void setBody(DefaultRequest awsRequest, @Nullable RequestBody body) throws IOException {
        if (body == null) {
            return;
        }
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        awsRequest.setContent(new ByteArrayInputStream(buffer.readByteArray()));
        awsRequest.addHeader("Content-Length", String.valueOf(body.contentLength()));
        buffer.close();
    }

    private void applyAwsHeaders(Request.Builder builder, Map<String, String> headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.header(header.getKey(), header.getValue());
        }
        builder.header("Content-Type", "application/json");
    }
}
