package com.makiia.job_service.service;

import com.makiia.job_service.client.FileStorageClient;
import com.makiia.job_service.exc.NotFoundException;
import com.makiia.job_service.model.Category;
import com.makiia.job_service.model.Job;
import com.makiia.job_service.repository.JobRepository;
import com.makiia.job_service.request.job.JobCreateRequest;
import com.makiia.job_service.request.job.JobUpdateRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CategoryService categoryService;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    public Job createJob(JobCreateRequest request, MultipartFile file) {
        Category category = categoryService.getCategoryById(request.getCategoryId());

        String imageId = null;

        if (file != null)
            imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();

        return jobRepository.save(Job.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .keys(Optional.of(List.of(request.getKeys()))
                        .orElse(new ArrayList<>()))
                .imageId(imageId)
                .build());
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    public Job getJobById(String id) {
        return findJobById(id);
    }

    public Job updateJob(JobUpdateRequest request, MultipartFile file) {
        Job toUpdate = findJobById(request.getCategoryId());
        modelMapper.map(request, toUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getImageId());
                toUpdate.setImageId(imageId);
            }
        }

        return jobRepository.save(toUpdate);
    }

    public void deleteJobById(String id) {
        jobRepository.deleteById(id);
    }

    public List<Job> getJobsByCategoryId(String id) {
        return jobRepository.getJobsByCategoryId(id);
    }

    public List<Job> getJobsThatFitYourNeeds(String needs) {
        String[] keys = needs.replaceAll("\"", "").split(" ");
        HashMap<String, Integer> map = new HashMap<>();
        Arrays.stream(keys).forEach(key -> jobRepository.getJobsByKeysContainsIgnoreCase(key)
                .forEach(job -> {
                    if (map.containsKey(job.getId())) {
                        int count = map.get(job.getId());
                        map.put(job.getId(), count + 1);
                    } else map.put(job.getId(), 1);
                }));
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> findJobById(entry.getKey()))
                .collect(Collectors.toList());
    }

    protected Job findJobById(String id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));
    }

}
