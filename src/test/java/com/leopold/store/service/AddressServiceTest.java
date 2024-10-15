package com.leopold.store.service;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AddressServiceTest {
    @Autowired
    IAddressService addressService;

    private final Integer testUserID = 13;
    private final String testUsername = "leopold";
    private final Integer defaultAddressID = 24;
    private final Integer currentAddressNumber = 3;



    @Test
    public void testFoundDefaultAddresses() {
        List<Address> addresses = addressService.findDefaultAddressByUser(testUserID);
        assertNotEquals(addresses.size(),0);
    }

    @Test
    public void testSetDefaultAddress() {
        addressService.setDefaultAddress(defaultAddressID, testUserID, "admin");

        List<Address> addresses = addressService.findDefaultAddressByUser(testUserID);
        assertEquals(addresses.size(), 1);
        assertEquals(addresses.getFirst().getId(), defaultAddressID);
    }


    @Test
    public void testDeleteAddress() {
        addressService.deleteAddressByID(defaultAddressID, testUserID, testUsername);

        assertEquals(addressService.findAddressByUser(testUserID).size(), currentAddressNumber-1);
        assertNull(addressService.findAddressByID(defaultAddressID));
        /*
INSERT INTO t_address
VALUES (NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13'),
(NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13'),
(NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13'),
(NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13'),
(NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13'),
(NULL, 13, 'leopold_delete','删除省', '320001', '删除市', '321101', '删除区', '321112', '212222', '删除大路666号', '19999999999', '88888888', 'delete', 0, 'test001', '2024-09-04 01:22:52', 'test001', '2024-09-06 17:05:13');
         */
    }
}
