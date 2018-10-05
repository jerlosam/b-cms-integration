package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDetails {
    public String name;
    public String address;
    public String address2;
    public String postalCode;
    public String phone;
    public String email;
    public String birthDate;
    public String age;
}
