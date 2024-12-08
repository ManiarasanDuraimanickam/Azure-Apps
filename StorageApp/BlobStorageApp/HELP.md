# Commands to create Azure storage accounts and containers
## 1. Create Storage Account
* az group create --name {groupName} --location {location}

* az storage account create --name {storageAccountName} --resource-group {resourceGroupName} --sku {skuType}

* az storage account show-connection-string --account-name {storageAccountName} --resource-group {resourceGroupName}

<b> <ins>copy connection string value</ins></b>

# 2. Create Blob Container
* az storage container create --name {containerName} --account-name {accountName} --resource-group {resourceGroupName} --auth-mode login --sas-token {pass the AccountKey value from the connection string which you have copied from the above command}

# 3. Copy between the Storage account & between the containers
<ins><b>Create one more storage account to understand the copy functionality between the storage account and the containers</b></ins>

* azcopy copy [srcStorageAccountWithSAS] [destStorageAccountWithSAS] --recursive=true
<ins>while create sas token , check only blob and check all "allowed resources types"</ins>
* replace srcStorageAccountWithSAS= {Blob service SAS URL}, destStorageAccountWithSAS= {Blob service SAS URL}
