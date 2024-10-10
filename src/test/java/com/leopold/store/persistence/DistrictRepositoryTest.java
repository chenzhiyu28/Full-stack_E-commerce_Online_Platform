package com.leopold.store.persistence;

import com.leopold.store.entity.District;
import com.leopold.store.persistence.repository.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
public class DistrictRepositoryTest {
    @Autowired
    private DistrictRepository repository;

    @Test
    public void findDistrictByParentTest() {
        List<District> districts = repository.findDistrictByParent("210100");

        assertNotNull(districts);

        for (District district: districts) {
            System.out.println(district);
        }
    }

    @Test
    public void findDistrictByCodeTest() {
        District district = repository.findDistrictByCode("110101");

        assertNotNull(district);
        assertEquals("110101", district.getCode());
    }

    @Test
    public void findDistrictNameByCodeTest() {
        String name = repository.findDistrictNameByCode("110101");

        assertNotNull(name);
        assertEquals("东城区", name);
    }
}
