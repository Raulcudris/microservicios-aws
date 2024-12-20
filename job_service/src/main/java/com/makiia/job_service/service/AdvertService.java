package com.makiia.job_service.service;

import com.makiia.job_service.client.FileStorageClient;
import com.makiia.job_service.client.UserServiceClient;
import com.makiia.job_service.dto.UserDto;
import com.makiia.job_service.enums.AdvertStatus;
import com.makiia.job_service.enums.Advertiser;
import com.makiia.job_service.exc.NotFoundException;
import com.makiia.job_service.model.Advert;
import com.makiia.job_service.model.Job;
import com.makiia.job_service.repository.AdvertRepository;
import com.makiia.job_service.request.advert.AdvertCreateRequest;
import com.makiia.job_service.request.advert.AdvertUpdateRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final JobService jobService;
    private final UserServiceClient userServiceclient;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    public Advert createAdvert(AdvertCreateRequest request, MultipartFile file) {
        String userId = getUserById(request.getUserId()).getId();
        Job job = jobService.getJobById(request.getJobId());

        String imageId = null;

        if (file != null)
            imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();

        Advert toSave = Advert.builder()
                .userId(userId)
                .job(job)
                .name(request.getName())
                .advertiser(request.getAdvertiser())
                .deliveryTime(request.getDeliveryTime())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(AdvertStatus.OPEN)
                .imageId(imageId)
                .build();
        return advertRepository.save(toSave);
    }

    public List<Advert> getAll() {
        return advertRepository.findAll();
    }

    public Advert getAdvertById(String id) {
        return findAdvertById(id);
    }

    public List<Advert> getAdvertsByUserId(String id, Advertiser type) {
        String userId = getUserById(id).getId();
        return advertRepository.getAdvertsByUserIdAndAdvertiser(userId, type);
    }

    public UserDto getUserById(String id) {
        return Optional.ofNullable(userServiceclient.getUserById(id).getBody())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Advert updateAdvertById(AdvertUpdateRequest request, MultipartFile file) {
        Advert toUpdate = findAdvertById(request.getId());
        modelMapper.map(request, toUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getImageId());
                toUpdate.setImageId(imageId);
            }
        }

        return advertRepository.save(toUpdate);
    }

    public void deleteAdvertById(String id) {
        advertRepository.deleteById(id);
    }

    public boolean authorizeCheck(String id, String principal) {
        return getUserById(getAdvertById(id).getUserId()).getUsername().equals(principal);
    }

    protected Advert findAdvertById(String id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advert not found"));
    }
}
