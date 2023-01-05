package com.example.project.client;

import com.example.project.models.Register;
import com.example.project.models.SendVerify;
import com.example.project.models.SignIn;
import com.example.project.models.Verify;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Interface {

    @POST("register")
    Call<Register> register(@Body Register sign);

    @POST("verifyUser")
    Call<Verify> verifyUser(@Body Verify sign);

    @POST("login")
    Call<SignIn> signIn(@Body SignIn signIn);

    @POST("resendVerificationCode")
    Call<SendVerify> resendVerification(@Body SendVerify sign);

}
