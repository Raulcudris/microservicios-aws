package com.makiia.job_service.request.offer;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OfferUpdateRequest {
    @NotBlank(message = "Offer id is required")
    private String id;
    private int offeredPrice;
}
