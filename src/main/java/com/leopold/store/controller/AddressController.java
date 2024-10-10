package com.leopold.store.controller;


import com.leopold.store.entity.Address;
import com.leopold.store.entity.DTO.AddressDTO;
import com.leopold.store.service.IAddressService;
import com.leopold.store.service.IUserService;
import com.leopold.store.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/addresses")
//@CrossOrigin(origins = "http://localhost:63342")
public class AddressController extends BaseController {
    private final IAddressService addressService;
    private final IUserService userService;

    public AddressController(IAddressService addressService, IUserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping({"", "/"})
    public JsonResponse<Address> addNewAddress(@RequestBody Address address, HttpSession session) {
        Integer userId = getUIDFromSession(session);
        String userName = getUsernameFromSession(session);
        System.out.println(address);
        return successResponse(addressService.addNewAddress(userId, userName, address));
    }

    @GetMapping({"", "/"})
    public JsonResponse<List<AddressDTO>> getAddressById(HttpSession session) {
        Integer userId = getUIDFromSession(session);

        return successResponse(addressService.findAddressByUser(userId));
        // localhost:8080/addresses/
    }

    @PutMapping({"{aid}", "{aid}/"})
    public JsonResponse<AddressDTO> setDefaultAddress(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUIDFromSession(session);
        String username = getUsernameFromSession(session);
        AddressDTO addressDTO = addressService.setDefaultAddress(aid, uid, username);

        return successResponse(addressDTO);
    }

    @DeleteMapping({"{aid}", "{aid}/"})
    public JsonResponse<Void> deleteAddress(@PathVariable Integer aid, HttpSession session) {
        Integer uid = getUIDFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.deleteAddressByID(aid, uid, username);

        return successResponse(null);
    }
}
