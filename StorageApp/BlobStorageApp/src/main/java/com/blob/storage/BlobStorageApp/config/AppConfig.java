package com.blob.storage.BlobStorageApp.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
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

    @Bean
    public QueueServiceClient getQueueServiceClient(
            @Value("${azure.storage.blob.sas-token") String sasToken,
                                                    @Value("${azure.storage.connection-string}") String connectionString){
        return new QueueServiceClientBuilder().sasToken(sasToken)
                .connectionString(connectionString).buildClient();
    }

}

