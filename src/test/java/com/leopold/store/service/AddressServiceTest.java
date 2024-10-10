package com.leopold.store.service;

import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class AddressServiceTest {
    @Autowired
    IAddressService addressService;

    @Test
    public void testFoundDefaultAddresses() {
        List<Address> addresses = addressService.findDefaultAddressByUser(13);
        assertEquals(addresses.size(), 3);
    }

    @Test
    public void testSetDefaultAddress() {
        addressService.setDefaultAddress(16, 13, "admin");

        List<Address> addresses = addressService.findDefaultAddressByUser(13);
        assertEquals(addresses.size(), 1);
        assertEquals(addresses.getFirst().getId(), 16);
    }


    @Test
    public void testDeleteAddress() {
        Integer aid = 15;
        String username = "leopold";
        Integer uid = 13;

        addressService.deleteAddressByID(aid, uid, username);

        assertEquals(addressService.findAddressByUser(uid).size(), 2);
        assertNull(addressService.findAddressByID(15));
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
