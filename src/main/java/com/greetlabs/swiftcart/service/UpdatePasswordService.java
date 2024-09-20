package com.greetlabs.swiftcart.service;


import java.util.Map;


public interface UpdatePasswordService {
    public String userEmailVerificationForUpdatePassword(String email) ;
    public String updatePassword(String token, Map<String,String> password) ;
    public void  UpdateTemporaryToken(String email , String token);
    boolean validateTemporaryToken(String token);

}
