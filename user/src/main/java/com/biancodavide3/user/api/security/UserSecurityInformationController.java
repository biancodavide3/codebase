package com.biancodavide3.user.api.security;

import com.biancodavide3.clients.user.UserSecurityInformationRequest;
import com.biancodavide3.clients.user.UserSecurityInformationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security")
@AllArgsConstructor
public class UserSecurityInformationController {

    private final UserSecurityInformationService service;

    @PostMapping("check")
    public ResponseEntity<UserSecurityInformationResponse> getUserSecurityInformation(
            @RequestBody UserSecurityInformationRequest request) {
        return service.getUserSecurityInformation(request);
    }

    @PostMapping("add")
    public ResponseEntity<UserSecurityInformationResponse> addUserSecurityInformation(
            @RequestBody UserSecurityInformationRequest request) {
        return service.addUserSecurityInformation(request);
    }

}
