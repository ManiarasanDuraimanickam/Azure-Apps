package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.BlobTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class BlobTriggerFunction {

    //@FunctionName("blobTriggerFunction")
    public void execute(
            @BlobTrigger(name = "blobTrigger",
                    dataType = "binary",
                    path = "az204-userblobcontainer/{blobName}",
                    connection = "AzureWebJobsStorage") byte[] blobData,
            @BindingName("blobName") String blobName,
            final ExecutionContext context) throws IOException {
        context.getLogger().info("Executing blob trigger");
        Path path = Files.createFile(Path.of("C:\\Users\\LENOVO\\Downloads\\" + blobName));
        Files.write(path, blobData, StandardOpenOption.WRITE);
        context.getLogger().info("file has been created in " + path.toString());
    }
}
