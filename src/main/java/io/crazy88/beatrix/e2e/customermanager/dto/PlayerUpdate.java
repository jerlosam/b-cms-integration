package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerUpdate {

    public String firstName;
    public String lastName;
    public String username;
    public String birthDate;
    public String address;
    public String state;
    public String city;
    public String postalCode;
    public String phone;
    public String secondaryPhone;
    public String email;
}
