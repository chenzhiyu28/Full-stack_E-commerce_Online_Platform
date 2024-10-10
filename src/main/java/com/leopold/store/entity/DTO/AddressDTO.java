package com.leopold.store.entity.DTO;

import com.leopold.store.entity.Address;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer aid;
    private String tag;
    private String name;
    private String phone;
    private String fullAddress;
    private Integer isDefault;

    public AddressDTO(Address address) {
        try {
            this.aid = address.getId();
            this.tag = address.getTag();
            this.name = address.getName();
            this.phone = address.getPhone();
            this.fullAddress = address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress();
            this.isDefault = address.getIsDefault();
        }catch (Exception e) {
            throw new IllegalArgumentException("Failed to transfer to AddressDTO due to illegal values");
        }
    }
}
