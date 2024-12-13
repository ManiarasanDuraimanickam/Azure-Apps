package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggerFunction {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTriggerFunction")
    public void run(
        @QueueTrigger( name = "message", queueName = "az204-queue", connection = "AzureWebJobsStorage") String message,
       @QueueOutput(name = "queueOutput", connection = "AzureWebJobsStorage", queueName = "az204-queue") OutputBinding<String> queueOutput,
        final ExecutionContext context) {
        context.getLogger().info("Java Queue trigger function processed a message: " + message);
        queueOutput.setValue("message processed successfully");
    }
}
