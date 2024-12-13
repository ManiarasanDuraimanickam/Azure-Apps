package com.blob.storage.BlobStorageApp.rest;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.SendMessageResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage/queue")
@Tag(name = "Azure Storage Queue", description = "Azure Storage Queue related operations are here")
public class QueueStorageController {

    private final QueueServiceClient queueServiceClient;
    private final String queueName;

    public QueueStorageController(QueueServiceClient queueServiceClient,
                                  @Value("${azure.storage.queue-name}") String queueName) {
        this.queueServiceClient = queueServiceClient;
        this.queueName = queueName;
    }

    @PostMapping("/post")
    public String postMsg(@Parameter(name = "message", required = true, description = "pass the message to be posted to the queue")
                          @RequestParam("message") String message) {
        QueueClient queueClient = this.queueServiceClient.getQueueClient(this.queueName);
        SendMessageResult messageResult = queueClient.sendMessage(message);
        return "Message Published, Id- " + messageResult.getMessageId() + "Expire on" + messageResult.getExpirationTime() + " , Inserted on " + messageResult.getInsertionTime();

    }

    @GetMapping("/get")
    public String getMsg(){
        QueueClient queueClient = this.queueServiceClient.getQueueClient(this.queueName);
        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
        return "Id - "+queueMessageItem.getMessageId() +" - msg : "+queueMessageItem.getMessageText();
    }
}
