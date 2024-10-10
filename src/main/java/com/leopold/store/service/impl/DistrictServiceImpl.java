package com.leopold.store.service.impl;

import com.leopold.store.entity.District;
import com.leopold.store.persistence.repository.DistrictRepository;
import com.leopold.store.service.IDistrictService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    private final DistrictRepository repository;

    public DistrictServiceImpl(DistrictRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<District> findDistrictByParent(String parent) {
        List<District> districts = repository.findDistrictByParent(parent);

        for (District district: districts) {
            district.setParent(null);
            district.setId(null);
        }

        return districts;
    }

    @Override
    public String findDistrictNameByCode(String code) {
        return repository.findDistrictNameByCode(code);
    }
}
