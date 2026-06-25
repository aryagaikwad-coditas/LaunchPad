package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.CreateEmployeeRequest;
import com.example.LaunchPad.dto.response.EmployeeResponse;
import com.example.LaunchPad.service.HrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
public class HrController {

    private final HrService hrService;

    @PostMapping("/employee")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid
                                                           @RequestBody CreateEmployeeRequest request){
        return ResponseEntity.ok(hrService.createEmployee(request));
    }
}
