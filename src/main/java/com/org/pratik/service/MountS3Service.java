package com.org.pratik.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
public class MountS3Service {

	@Value("${source.bucket}")
	String sourceBucket;
	
	@Autowired
	AmazonS3 s3Client;
	
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
    	
    	File orgFile=null;
		try {
			orgFile = convertMultiPartToFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println("1) "+file.getContentType());
    	System.out.println("2) "+file.getOriginalFilename());
    	uploadFileTos3bucket(s3Client, file.getOriginalFilename(), orgFile, sourceBucket, file.getContentType());
    	return "Ok";
    }
    
    @GetMapping("/listFile")
    public List<String> listFile() {
    	
    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(sourceBucket);
		List<String> keys = new ArrayList<String>();

		ObjectListing objects = s3Client.listObjects(listObjectsRequest);
		for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
			String object = objectSummary.getKey();
			keys.add(object);
		}
		
		for (String fileName : keys) {
			S3Object s3Object = s3Client.getObject(new GetObjectRequest(sourceBucket, fileName));
			if ("text/csv".equals(s3Object.getObjectMetadata().getContentType())) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
				String content = null;
				try {
					while ((content=reader.readLine())!=null) {
						// printing the content 
						System.out.println(content);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return keys;
    }

    public void uploadFileTos3bucket(AmazonS3 s3Client, String fileName, File file, String bucketName, String contentType) {
		
		ObjectMetadata metadataCopy = new ObjectMetadata();
		metadataCopy.setContentType(contentType);
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, file).withMetadata(metadataCopy)
	            .withCannedAcl(CannedAccessControlList.PublicRead) );
	}
	
	public File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}

}
