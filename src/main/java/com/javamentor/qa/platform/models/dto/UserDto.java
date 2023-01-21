package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String city;
    private String imageLink;
    private int reputation;

    public UserDto(Long id, String email, String fullName, String city, String imageLink) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.city = city;
        this.imageLink = imageLink;
    }

    //    public UserDto() {
//    }
//
//    public UserDto(Long id, String email, String fullName, String city, String imageLink, int reputation) {
//        this.id = id;
//        this.email = email;
//        this.fullName = fullName;
//        this.city = city;
//        this.imageLink = imageLink;
//        this.reputation = reputation;
//    }
}