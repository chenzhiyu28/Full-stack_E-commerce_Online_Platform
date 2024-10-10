package com.leopold.store.persistence.repository;

import com.leopold.store.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {
    List<District> findDistrictByParent(String parent);

    District findDistrictByCode(String code);

    @Query("SELECT d.name FROM District d WHERE d.code = :code")
    String findDistrictNameByCode(@Param("code") String code);
}
