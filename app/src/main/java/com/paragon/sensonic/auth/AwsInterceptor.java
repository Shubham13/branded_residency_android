package com.paragon.sensonic.auth;

import androidx.annotation.Nullable;

import com.amazonaws.DefaultRequest;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.Regions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static com.paragon.sensonic.network.Constant.POOL_ID;
import static com.paragon.sensonic.network.Constant.REGION;
import static com.paragon.sensonic.network.Constant.SERVICE_NAME;
import static com.paragon.sensonic.utils.AppConstant.EMPTY;


class AwsInterceptor implements Interceptor {

    private final CognitoCredentialsProvider credentialsProvider;
    private final AwsSigner signer;
    private final RequestBody requestBody;
    private final HttpMethodName httpMethodName;
    private final String method, baseUrl;
    private final String contentType = "application/json";


    public AwsInterceptor(String baseUrl, HttpMethodName methodType, EndPoints endPoint, String body) {
        this.credentialsProvider = new CognitoCredentialsProvider(POOL_ID, Regions.AP_SOUTH_1);
        this.httpMethodName = methodType;
        this.baseUrl = baseUrl;
        this.requestBody = RequestBody.create(MediaType.parse(contentType), body);
        this.method = endPoint.getValue();
        this.signer = new AwsSigner();
        this.signer.setServiceName(SERVICE_NAME);
        this.signer.setRegionName(REGION);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getMethod() {
        return method;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getHttpMethodName() {
        return httpMethodName.name();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request signedRequest = sign(chain.request());
        return chain.proceed(signedRequest);
    }


    private Request sign(Request request) throws IOException {
        Request.Builder builder = request.newBuilder();
        DefaultRequest defaultRequest = new DefaultRequest(SERVICE_NAME);
        defaultRequest.setEndpoint(URI.create(baseUrl + EMPTY + method));
        defaultRequest.setHttpMethod(httpMethodName);
        setBody(defaultRequest, requestBody);
        signer.sign(defaultRequest, credentialsProvider.getCredentials());
        applyAwsHeaders(builder, defaultRequest.getHeaders());
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
        builder.header("Content-Type", contentType);
    }
}
