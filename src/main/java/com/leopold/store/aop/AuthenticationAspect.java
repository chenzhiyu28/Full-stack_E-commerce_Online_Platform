package com.leopold.store.aop;

import com.leopold.store.aop.annotation.ValidateAddressDelete;
import com.leopold.store.entity.Address;
import com.leopold.store.persistence.repository.AddressRepository;
import com.leopold.store.service.ex.AddressNotExistException;
import com.leopold.store.service.ex.IllegalRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthenticationAspect {
    private final AddressRepository addressRepository;

    public AuthenticationAspect(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Before("@annotation(validateAddressDelete)")
    public void validateAddress(JoinPoint joinPoint, ValidateAddressDelete validateAddressDelete) {
        System.out.println("AOP validation running!");
        Object[] args = joinPoint.getArgs();
        Integer aid = (Integer) args[0];
        Integer uid = (Integer) args[1];

        Address address = addressRepository.findAddressById(aid);
        if (address == null) {
            throw new AddressNotExistException("Address does not exist!");
        } else if (!address.getUser().getId().equals(uid)) {
            throw new IllegalRequestException("Request is unauthorized!");
        }
    }
}