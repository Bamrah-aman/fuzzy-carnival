package com.grabbingthecode.authservice.user;


import lombok.Data;

@Data

public class ChangePasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
