package com.vata.profile.infrastructure;

import com.vata.common.configuration.FeignConfig;
import jakarta.annotation.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(
        name = "stabilityFeignClient",
        url = "${stability.api.base-url}",
        configuration = FeignConfig.class
)
public interface StabilityImageFeignClient {

    @PostMapping(
            value = "/v2beta/stable-image/generate/core",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    ResponseEntity<byte[]> generateImage(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("accept") String accept,
            @RequestPart("none") Resource dummyFile,
            @RequestPart("prompt") String prompt,
            @RequestPart("output_format") String outputFormat
    );
}
