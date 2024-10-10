package com.leopold.store.service;

import com.leopold.store.entity.District;

import java.util.List;

public interface IDistrictService {
    List<District> findDistrictByParent(String parent);

    String findDistrictNameByCode(String code);
}
