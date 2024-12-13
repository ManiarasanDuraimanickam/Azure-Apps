package com.blob.storage.BlobStorageApp.rest;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/storage/blob")
public class BlobStorageController {

    private final BlobServiceClient blobServiceClient;
    private final String blobContainer;

    @Autowired
    public BlobStorageController(BlobServiceClient blobServiceClient,
                                 @Value("${azure.storage.blob.container}") String blobContainer) {
        this.blobServiceClient = blobServiceClient;
        this.blobContainer = blobContainer;
    }

    @GetMapping("/list")
    @OpenAPI30
    public Map<String, List<String>> getBlob() {
        PagedIterable<BlobContainerItem> blobContainerClient = this.blobServiceClient.listBlobContainers();
        return blobContainerClient.stream().map(pg -> pg.getName())
                .map(name -> {
                    BlobContainerClient containerClient = this.blobServiceClient.getBlobContainerClient(name);
                    return new Object[]{name, containerClient};
                })
                .collect(Collectors.groupingBy(object -> (String) object[0],
                        Collectors.mapping(object -> {
                                    BlobContainerClient containerClient = (BlobContainerClient) object[1];
                                    return containerClient.listBlobs().stream().map(BlobItem::getName).collect(Collectors.joining(","));
                                },
                                Collectors.toList())));

    }

    @PostMapping(value = "/add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @OpenAPI30()
    public String addBlob(@RequestPart @RequestBody(description = "upload a file") MultipartFile multipartFile) throws IOException {
       if(null == multipartFile || multipartFile.isEmpty()){
           return "upload a valid multipart file";
       }

       BlobContainerClient blobContainerClient = this.blobServiceClient.getBlobContainerClient(this.blobContainer);
       BlobClient blobClient = blobContainerClient.getBlobClient(multipartFile.getOriginalFilename());
       blobClient.upload(multipartFile.getInputStream());
       return "uploaded the file";
    }
}
