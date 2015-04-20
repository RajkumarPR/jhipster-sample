package com.urbanbinge.web.rest.dto;

import org.hibernate.validator.constraints.Email;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(min = 2, max = 5)
    private String langKey;

    private String profilePic;

    private String website;

    private String mobileNo;

    private String city;

    private String expertise;

    private String aboutMe;

    private String nickName;

    private LocalDate dob;

    private List<String> roles;

    public UserDTO() {
    }

    public UserDTO(String login, String password, String
                   firstName, String lastName, String email,
                   String langKey,String profilePic,
                   String website, String mobileNo, String  city,
                   String expertise, String aboutMe, String nickName,
                   LocalDate dob, List<String> roles) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.langKey = langKey;
        this.profilePic = profilePic;
        this.website = website;
        this.mobileNo = mobileNo;
        this.city = city;
        this.expertise = expertise;
        this.aboutMe = aboutMe;
        this.nickName = nickName;
        this.dob = dob;
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getWebsite() {
        return website;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getCity() {
        return city;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDate getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
        "login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", langKey='" + langKey + '\'' +
        ", profilePic='" + profilePic + '\'' +
        ", website='" + website + '\'' +
        ", mobileNo='" + mobileNo + '\'' +
        ", city='" + city + '\'' +
        ", expertise='" + expertise + '\'' +
        ", aboutMe='" + aboutMe + '\'' +
        ", nick_name='" + nickName + '\'' +
        ", dob='" + dob + '\'' +
        ", roles=" + roles +
        '}';
    }
}
