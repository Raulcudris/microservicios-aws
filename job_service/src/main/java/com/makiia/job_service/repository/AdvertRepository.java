package com.makiia.job_service.repository;

import com.makiia.job_service.enums.Advertiser;
import com.makiia.job_service.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, String> {
    List<Advert> getAdvertsByUserIdAndAdvertiser(String id, Advertiser advertiser);
}