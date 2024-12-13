package com.function;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.StorageAccount;
import com.microsoft.azure.functions.annotation.TimerTrigger;

import java.util.Optional;
import java.util.logging.Level;

public class TimerTriggerFunc {


    //@FunctionName("TimeTriggerFunc")
    @StorageAccount("AzureWebJobsStorage")
    public void execute(@TimerTrigger(name = "timerTriggerFunc", schedule = "0 0/2 * * * *") String timerInfo,
                        final ExecutionContext context) {
        context.getLogger().log(Level.INFO, "Timer triggered " + timerInfo);
        String sasToken = "W2rFC5OVEW7oZEyNaDaDKZTBqWRNPCRwAIa6fzvPjvJms/u69QS18xe5kNhvqflPIeX7Mk3GIN/Q+AStQLOY0w==";
        String azureWebJobsStorage = "DefaultEndpointsProtocol=https;EndpointSuffix=core.windows.net;AccountName=az204freestorageacct;AccountKey=W2rFC5OVEW7oZEyNaDaDKZTBqWRNPCRwAIa6fzvPjvJms/u69QS18xe5kNhvqflPIeX7Mk3GIN/Q+AStQLOY0w==;BlobEndpoint=https://az204freestorageacct.blob.core.windows.net/;FileEndpoint=https://az204freestorageacct.file.core.windows.net/;QueueEndpoint=https://az204freestorageacct.queue.core.windows.net/;TableEndpoint=https://az204freestorageacct.table.core.windows.net/";
        context.getLogger().log(Level.INFO, "AzureWebJobsStorage " + azureWebJobsStorage);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().sasToken(sasToken).connectionString(azureWebJobsStorage)
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("az204-adminblobcontainer");
        Optional<String> blobNameOpt = blobContainerClient.listBlobs().stream().map(blobItem -> blobItem.getName()).findAny();
        if (blobNameOpt.isPresent()) {
            BlobClient blobClient = blobContainerClient.getBlobClient(blobNameOpt.get());
            BlobContainerClient userContainerClient = blobServiceClient.getBlobContainerClient("az204-userblobcontainer");
            BlobClient userBlobClient = userContainerClient.getBlobClient(blobNameOpt.get());
            userBlobClient.upload(blobClient.openInputStream());
        } else
            context.getLogger().log(Level.INFO, "Blob empty");

        context.getLogger().log(Level.INFO, "Timer Trigger has been completed successfully ");
    }
}
