package com.kiingdom.streamingsite.service.aws;

import java.io.IOException;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.model.CreateInvalidationRequest;
import com.amazonaws.services.cloudfront.model.InvalidationBatch;
import com.amazonaws.services.cloudfront.model.Paths;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSService {
    
    private final AmazonS3 amazonS3;
    private final AmazonElasticTranscoder elasticTranscoder;
    private final AmazonCloudFront cloudFront;

    private static final Logger logger = LoggerFactory.getLogger(AWSService.class);

    @Value("${aws.s3.bucket}")
    private String s3Bucket;

    @Value("${aws.elastictranscoder.pipeline}")
    private String pipelineId;

    @Value("${aws.cloudfront.distributionId}")
    private String distributionId;

    public AWSService(AmazonS3 amazonS3, AmazonElasticTranscoder elasticTranscoder, AmazonCloudFront cloudFront) {
        this.amazonS3 = amazonS3;
        this.elasticTranscoder = elasticTranscoder;
        this.cloudFront = cloudFront;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("upload_", "_temp");
            file.transferTo(tempFile);
            String fileName = generateFileName(file);
            amazonS3.putObject(new PutObjectRequest(s3Bucket, fileName, tempFile.toFile()));
            return fileName;
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch(IOException e) {
                    logger.warn("Failed to delete temporary file: {}", tempFile, e);
                }
            }
        }
    }

    public String transcodeVideo(String inputKey, String outputKey) {
        CreateJobRequest createJobRequest = new CreateJobRequest()
            .withPipelineId(pipelineId)
            .withInput(new JobInput().withKey(inputKey))
            .withOutput(new CreateJobOutput().withKey(outputKey).withPresetId("1351620000001-000001"));

        CreateJobResult result = elasticTranscoder.createJob(createJobRequest);
        return result.getJob().getId();
    }

    public void invalidateCloudFrontCache(String objectPath) {
        CreateInvalidationRequest invalidationRequest = new CreateInvalidationRequest()
            .withDistributionId(distributionId)
            .withInvalidationBatch(
                new InvalidationBatch()
                    .withPaths(new Paths().withItems("/" + objectPath).withQuantity(1))
                    .withCallerReference(String.valueOf(System.currentTimeMillis()))
            );
        cloudFront.createInvalidation(invalidationRequest);
    }

    private String generateFileName(MultipartFile multiPart) {
        return UUID.randomUUID().toString() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
