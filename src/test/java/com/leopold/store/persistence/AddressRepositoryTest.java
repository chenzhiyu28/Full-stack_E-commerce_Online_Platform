package com.leopold.store.persistence;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.AddressRepository;
import com.leopold.store.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAddress() {
        User user = userRepository.findByUsername("test001");

        Address address = Address.builder().user(user).name("testAddress").build();
        Address savedAddress = addressRepository.save(address);

        assertNotNull(savedAddress.getId());
        assertEquals(savedAddress.getName(), "testAddress");
    }

    @Test
    public void testAddressCount() {
        Integer count = addressRepository.countByUser(userRepository.findUserById(13));
        System.out.println(count);

        assertNotEquals(count, 0);
    }

    @Test
    public void testFoundDefaultAddressByUser() {
        User user = userRepository.findUserById(13);
        List<Address> addresses = addressRepository.findAddressByUserAndIsDefault(user, 1);
        assertEquals(addresses.size(), 2);
    }

    @Test
    public void testDeleteByID() {
        Integer aid = 15;

        Integer deletedRows = addressRepository.deleteAddressById(aid);

        assertNull(addressRepository.findAddressById(15));
        assertEquals(deletedRows, 1);
    }
}
