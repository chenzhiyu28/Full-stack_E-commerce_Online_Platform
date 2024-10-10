package com.leopold.store.persistence.repository;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
    Integer countByUser(User user);

    Address findAddressById(Integer aid);

    List<Address> findAddressByUser(User user);

    Integer deleteAddressById(Integer aid);

    Address findAddressByName(String name);

    List<Address> findAddressByUserOrderByIsDefaultDesc(User user);

    List<Address> findAddressByUserAndIsDefault(User user, Integer isDefault);

    List<Address> findAddressByUserOrderByModifiedTime(User user);
}
