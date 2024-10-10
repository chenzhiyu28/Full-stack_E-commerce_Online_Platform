package com.leopold.store.service;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.AddressDTO;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IAddressService {
    Address findAddressByID(Integer aid);

    Address addNewAddress(Integer uid, String userName, Address address);

    Address upDateAddress(Integer aid, String name, String phone, String address, Integer isDefault);

    List<AddressDTO> findAddressByUser(Integer userId);

    AddressDTO setDefaultAddress (Integer aid, Integer uid, @Param("modifiedUser") String modifiedUser);

    List<Address> findDefaultAddressByUser(Integer userId);

    void deleteAddressByID(Integer aid, Integer uid, String userName);
}
