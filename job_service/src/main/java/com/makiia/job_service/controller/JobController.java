package com.makiia.job_service.controller;

import com.makiia.job_service.dto.JobDto;
import com.makiia.job_service.request.job.JobCreateRequest;
import com.makiia.job_service.request.job.JobUpdateRequest;
import com.makiia.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/job-service/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JobDto> createJob(@Valid @RequestPart JobCreateRequest request,
                                     @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(jobService.createJob(request, file), JobDto.class));
    }

    @PostMapping("/getJobsThatFitYourNeeds/{needs}")
    ResponseEntity<List<JobDto>> getJobsThatFitYourNeeds(@PathVariable String needs) {
        return ResponseEntity.ok(jobService.getJobsThatFitYourNeeds(needs).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/getAll")
    ResponseEntity<List<JobDto>> getAll() {
        return ResponseEntity.ok(jobService.getAll().stream()
                .map(job -> modelMapper.map(job, JobDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/getJobById/{id}")
    ResponseEntity<JobDto> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(jobService.getJobById(id), JobDto.class));
    }

    @GetMapping("/getJobsByCategoryId/{id}")
    ResponseEntity<List<JobDto>> getJobsByCategoryId(@PathVariable String id) {
        return ResponseEntity.ok(jobService.getJobsByCategoryId(id).stream()
                .map(job -> modelMapper.map(job, JobDto.class)).collect(Collectors.toList()));
    }

    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JobDto> updateJob(@Valid @RequestPart JobUpdateRequest request,
                                     @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(jobService.updateJob(request, file), JobDto.class));
    }

    @DeleteMapping("/deleteJobById/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteJobById(@PathVariable String id) {
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }
}