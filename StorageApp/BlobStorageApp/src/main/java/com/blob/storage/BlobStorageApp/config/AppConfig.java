package com.blob.storage.BlobStorageApp.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableSwagger2
public class AppConfig {

    @Bean
    public BlobServiceClient getBlobServiceClient(
            @Value("${azure.storage.blob.sas-token") String sasToken,
            @Value("${azure.storage-account}") String storageAccount,
            @Value("${azure.storage.connection-string}") String connectionString
    ) {
        return new BlobServiceClientBuilder()
                .sasToken(sasToken)
                .connectionString(connectionString)
//                .containerName("az204freestorageacct")
                .buildClient();
    }
}

