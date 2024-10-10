package com.leopold.store.service.impl;

import com.leopold.store.aop.annotation.ValidateAddressDelete;
import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.AddressDTO;
import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.AddressRepository;
import com.leopold.store.persistence.repository.UserRepository;
import com.leopold.store.service.IAddressService;
import com.leopold.store.service.IDistrictService;
import com.leopold.store.service.ex.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final IDistrictService districtService;

    @Value("${user.address.max-capacity}")  // read data from application.properties
    private Integer MAX_ADDRESS_CAPACITY;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, IDistrictService districtService) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.districtService = districtService;
    }

    @Override
    public Address findAddressByID(Integer aid) {
        return addressRepository.findAddressById(aid);
    }

    @Override
    public Address addNewAddress(Integer uid, String userName, Address address) {
        if (uid == null) {
            throw new SessionExpiredException();
        }

        Integer count = addressRepository.countByUser(userRepository.findUserById(uid));
        if (count >= MAX_ADDRESS_CAPACITY) {
            throw new AddressCommitLimitException("A maximum of 20 addresses is reached!");
        }


        String provinceName = districtService.findDistrictNameByCode(address.getProvinceCode());
        String cityName = districtService.findDistrictNameByCode(address.getCityCode());
        String areaName = districtService.findDistrictNameByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUser(userRepository.findUserById(uid));
        address.setIsDefault(count.equals(0)? 1:0);  // set default if is the only address

        // log info
        address.setCreatedUser(userName);
        address.setCreatedTime(new Date());
        address.setModifiedUser(userName);
        address.setModifiedTime(new Date());

        return addressRepository.save(address);
    }

    @Override
    public Address upDateAddress(Integer aid, String name, String phone, String address, Integer isDefault) {
        return null;
    }

    @Override
    public List<AddressDTO> findAddressByUser(Integer userId) {
        User user = userRepository.findUserById(userId);

        List<AddressDTO> addresses = new ArrayList<>();
        for (Address address : addressRepository.findAddressByUserOrderByIsDefaultDesc(user)) {
            addresses.add(new AddressDTO(address));
        }

        return addresses;
    }

    @Override
    public AddressDTO setDefaultAddress(Integer aid, Integer uid, String modifiedUser) {
        Address selectedAddress = addressRepository.findAddressById(aid);

        if (selectedAddress == null) {
            throw new AddressNotExistException("Address does not exist!");
        } else if (!selectedAddress.getUser().getId().equals(uid)) {
            throw new IllegalRequestException("Request is Illegal!");
        }

        Date modifiedTime = new Date();

        List<Address> defaultAddresses = findDefaultAddressByUser(selectedAddress.getUser().getId());

        for (Address address : defaultAddresses) {
            if (address.getIsDefault() == 1) {
                address.setIsDefault(0);
                address.setModifiedUser(modifiedUser);
                address.setModifiedTime(modifiedTime);
                addressRepository.save(address);
            }
        }

        selectedAddress.setIsDefault(1);
        selectedAddress.setModifiedUser(modifiedUser);
        selectedAddress.setModifiedTime(modifiedTime);

        return new AddressDTO(addressRepository.save(selectedAddress));
    }

    @Override
    public List<Address> findDefaultAddressByUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        List<Address> addresses = addressRepository.findAddressByUserAndIsDefault(user, 1);
        return addresses;
    }

    @Transactional
    @Override
    @ValidateAddressDelete
    public void deleteAddressByID(Integer aid, Integer uid, String userName) {
        // 1. verify data
        if (addressRepository.findAddressById(aid) == null) {
            throw new AddressNotExistException("Address does not exist!");
        } else if (!addressRepository.findAddressById(aid).getUser().getId().equals(uid)) {
            throw new IllegalRequestException("Request is unauthorized!");
        }

        Integer deletedAddressCount = addressRepository.deleteAddressById(aid);

        // 2.make another address default(if there are other addresses)
        if (deletedAddressCount != 1) {
            throw new DatabaseConnectionException("Failed to commit changes!");
        }

        User user = userRepository.findUserById(uid);
        List<Address> addresses = addressRepository.findAddressByUserOrderByModifiedTime(user);
        if (!addresses.isEmpty()) {
            Address latestAddress = addresses.getFirst();
            latestAddress.setModifiedUser(userName);
            latestAddress.setModifiedTime(new Date());
            latestAddress.setIsDefault(1);
        }
    }


}
