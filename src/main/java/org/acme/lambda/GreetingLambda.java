package org.acme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class GreetingLambda implements RequestHandler<JsonObject, String> {

    @ConfigProperty(name = "app.env.bucketName")
    String bucket;

    @ConfigProperty(name = "app.env.key")
    String key;

    @Inject
    S3Client s3Client;

    @Override
    public String handleRequest(JsonObject input, Context context) {
        ResponseBytes<GetObjectResponse> s3Object = s3Client.getObjectAsBytes(
                GetObjectRequest
                        .builder()
                        .bucket(bucket)
                        .key(key)
                        .build()
        );
        return s3Object.asUtf8String();
    }
}
