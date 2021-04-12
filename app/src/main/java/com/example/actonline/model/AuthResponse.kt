package com.example.actonline.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Dinh Sam Vu on 4/12/2021.
 */
class AuthResponse(

    @SerializedName("token")
    val token: String,

    @SerializedName("privatetoken")
    val privateToken: String
)