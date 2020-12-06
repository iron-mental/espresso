package com.iron.espresso.ext

import com.google.gson.Gson
import com.iron.espresso.model.response.BaseResponse
import com.iron.espresso.model.response.ErrorResponse
import retrofit2.HttpException


fun HttpException.toErrorResponse(): ErrorResponse =
    Gson().fromJson(
        this.response()?.errorBody()?.string(),
        BaseResponse::class.java
    )