/*
 */
package com.nitsoft.util;
import com.amazonaws.AmazonClientException;
import java.io.File;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author johnny
 */
@Component
public class AmazonS3Util {

    @Autowired
    private AppConfig appConfig;       
    //AmazonS3Client
    private AmazonS3 s3Client;
    private String accessKey="";
    private String secretKey="";
    String bucketName="";
    
    //Init AmazonS3Client
    public AmazonS3Util(){}
    private void AmazonS3Setup() {
        if (s3Client == null) {
            AWSCredentials credentials = null;
            try {
                // Load config
                accessKey = appConfig.getValueOfKey("amazons3.accesskey");
                secretKey = appConfig.getValueOfKey("amazons3.secretkey");
                bucketName = appConfig.getValueOfKey("amazons3.bucket");
                credentials = new BasicAWSCredentials(accessKey, secretKey);
                s3Client = new AmazonS3Client(credentials);
            } catch (Exception e) {
                e.printStackTrace();
                // "Cannot load the credentials from the credential profiles file. ",e);
            }
        }
    }
  
   
    /**
     * downLoadToLocaServer
     * @param targetPath
     * @param keyName => the file name 
     */
    public boolean downLoadToLocaServer(String targetPath,String keyName, String fileName) {
        try {
            AmazonS3Setup();
            EventLogManager.getInstance().info("Process download from Amazon to local server key name="+ keyName);
            System.out.println("Downloading an object");
            S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
            System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
            object.getObjectContent();
            String targetFilePath=targetPath+fileName;
            File targetFile = new File(targetFilePath);
            FileUtils.copyInputStreamToFile(object.getObjectContent(), targetFile);
            
            // Check if it's PDF file => create thumnail file
            String fileExtension=fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
   
    
    /**
     * getURLDownload
     * @param keyName
     * @return 
     */
    public String getURLDownload(String keyName) {
        String urlString = "";
        try {
            AmazonS3Setup();
            System.out.println("Generating pre-signed URL.");
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += 3000 * 60 * 60; // Add 3 hours.
            expiration.setTime(milliSeconds);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            urlString = url.toString();
            EventLogManager.getInstance().info("Generating pre-signed URL Amazon="+urlString);
        } catch (Exception e) {
            EventLogManager.getInstance().error(e.getMessage());
            e.printStackTrace();
        }
        return urlString; 
    }
     
   
    /**
     * uploadFile
     * @param filePath
     * @param keyName: keyName=teamId + "/" + uploadFile.getFilePath(); 
     */
    // Set part size to 5 MB.
   // Fixed issue https://forums.aws.amazon.com/thread.jspa?threadID=91771
   long partSize = 5 * 1024 * 1024;
   public boolean uploadFile(String filePath,String keyName) {
        try {
            EventLogManager.getInstance().info("Process upload to Amazon key name="+ keyName);
            AmazonS3Setup();
            // Create a list of UploadPartResponse objects. You get one of these
            // for each part upload.
            List<PartETag> partETags = new ArrayList<PartETag>();
            // Step 1: Initialize.
            InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
            InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

            File file = new File(filePath);
            long contentLength = file.length();

            try {
                // Step 2: Upload parts.
                long filePosition = 0;
                for (int i = 1; filePosition < contentLength; i++) {
                    // Last part can be less than 5 MB. Adjust part size.
                    long _partSize = Math.min(partSize, (contentLength - filePosition));

                    // Create request to upload a part.
                    UploadPartRequest uploadRequest = new UploadPartRequest()
                            .withBucketName(bucketName)
                            .withKey(keyName)
                            .withUploadId(initResponse.getUploadId())
                            .withPartNumber(i)
                            .withFileOffset(filePosition)
                            .withFile(file)
                            .withPartSize(_partSize);

                    // Upload part and add response to our list.
                    partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
                    filePosition += _partSize;
                }

                // Step 3: Complete.
                CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(
                        bucketName,
                        keyName,
                        initResponse.getUploadId(),
                        partETags);

                s3Client.completeMultipartUpload(compRequest);
                
                return true;
            } catch (Exception e) {
                s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(
                        bucketName, keyName, initResponse.getUploadId()));
                EventLogManager.getInstance().error(e.getMessage());
                return false;
            }
        } catch (Exception e) {   
            e.printStackTrace();
            EventLogManager.getInstance().error(e.getMessage());
            return false;
        }

    } 
   
   /**
    * 
    * @param keyName: keyName=teamId + "/" + uploadFile.getFilePath(); 
    * @return 
    */
    public boolean deleteFile(String keyName) {
        
        try
        {
            AmazonS3Setup();
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
            EventLogManager.getInstance().info("Process delete file from Amazon key name="+ keyName);
            return true;
        }catch (AmazonClientException ace)
        {
            return  false;
        }     
    }
    
      
    /**
     * 
     * @param keyNames
     * @return 
     */
    public boolean deleteFiles(List<String> keyNames) {
            AmazonS3Setup();
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);
            List<KeyVersion> keys = new ArrayList<KeyVersion>();
            for (String keyItem : keyNames) {
                keys.add(new KeyVersion(keyItem));
            }
            multiObjectDeleteRequest.setKeys(keys);

            try {
                DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
                System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());

            } catch (MultiObjectDeleteException e) {
                // Process exception.
                 return false;
            }
            return true;     
    }
    
}
