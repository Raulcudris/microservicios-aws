package com.makiia.job_service.controller;


import com.makiia.job_service.dto.AdvertDto;
import com.makiia.job_service.enums.Advertiser;
import com.makiia.job_service.request.advert.AdvertCreateRequest;
import com.makiia.job_service.request.advert.AdvertUpdateRequest;
import com.makiia.job_service.service.AdvertService;
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
@RequestMapping("/v1/job-service/advert")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<AdvertDto> createAdvert(@Valid @RequestPart AdvertCreateRequest request,
                                                  @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(advertService.createAdvert(request, file), AdvertDto.class));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AdvertDto>> getAll() {
        return ResponseEntity.ok(advertService.getAll().stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/getAdvertById/{id}")
    public ResponseEntity<AdvertDto> getAdvertById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(advertService.getAdvertById(id), AdvertDto.class));
    }

    @GetMapping("/getAdvertsByUserId/{id}")
    public ResponseEntity<List<AdvertDto>> getAdvertsByUserId(@PathVariable String id,
                                                              @RequestParam Advertiser type) {
        return ResponseEntity.ok(advertService.getAdvertsByUserId(id, type).stream()
                .map(advert -> modelMapper.map(advert, AdvertDto.class)).collect(Collectors.toList()));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @advertService.authorizeCheck(#request.id, principal)")
    public ResponseEntity<AdvertDto> updateAdvertById(@Valid @RequestPart AdvertUpdateRequest request,
                                                      @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(advertService.updateAdvertById(request, file), AdvertDto.class));
    }

    @DeleteMapping("/deleteAdvertById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @advertService.authorizeCheck(#id, principal)")
    public ResponseEntity<Void> deleteAdvertById(@PathVariable String id) {
        advertService.deleteAdvertById(id);
        return ResponseEntity.ok().build();
    }
}