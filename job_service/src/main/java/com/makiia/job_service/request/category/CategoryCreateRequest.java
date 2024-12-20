package com.makiia.job_service.request.category;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryCreateRequest {
    @NotBlank(message = "Category name is required")
    private String name;
    private String description;
}
