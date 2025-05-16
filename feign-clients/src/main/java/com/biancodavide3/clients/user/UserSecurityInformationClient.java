package com.biancodavide3.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "userSecurityInformationClient",
        url = "http://localhost:8080"
)
public interface UserSecurityInformationClient {
    @PostMapping("api/v1/security/check")
    ResponseEntity<UserSecurityInformationResponse> getUserSecurityInformation(
            @RequestBody UserSecurityInformationRequest request);

    @PostMapping("api/v1/security/add")
    ResponseEntity<UserSecurityInformationResponse> addUserSecurityInformation(
            @RequestBody UserSecurityInformationRequest request);
}
