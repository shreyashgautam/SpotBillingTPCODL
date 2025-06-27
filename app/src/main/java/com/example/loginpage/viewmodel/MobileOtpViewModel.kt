package com.example.loginpage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.model.SendOtpRequest
import com.example.loginpage.model.VerifyOtpRequest
import com.example.loginpage.network.OtpRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MobileOtpViewModel : ViewModel() {

    // OTP send result: Pair(success, message)
    private val _sendOtpResult = MutableStateFlow<Pair<Boolean, String>?>(null)
    val sendOtpResult: StateFlow<Pair<Boolean, String>?> = _sendOtpResult

    // OTP verify result: Pair(success, message)
    private val _verifyOtpResult = MutableStateFlow<Pair<Boolean, String>?>(null)
    val verifyOtpResult: StateFlow<Pair<Boolean, String>?> = _verifyOtpResult


    fun sendOtp(email: String, mobile: String) {
        viewModelScope.launch {
            try {
                val request = SendOtpRequest(
                    CA = "80000427247",
                    EMAIL = email,
                    MOB_NUM = mobile,
                    REF_CODE = ""
                )
                Log.d("MobileOtpViewModel", "Sending OTP request: $request")
                val response = OtpRetrofitClient.api.sendOtp(request)
                Log.d("MobileOtpViewModel", "Response: ${response.body()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    val status = body?.response?.status ?: false
                    val message = body?.response?.message ?: "Unknown response"
                    Log.d("MobileOtpViewModel", "OTP send status: $status, message: $message")

                    _sendOtpResult.value = Pair(status, message)
                } else {
                    Log.e("MobileOtpViewModel", "Failed response: ${response.code()} ${response.message()}")
                    _sendOtpResult.value = Pair(false, "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MobileOtpViewModel", "Exception sending OTP", e)
                _sendOtpResult.value = Pair(false, "Exception: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    fun verifyOtp(mobile: String, otp: String) {
        viewModelScope.launch {
            try {
                val request = VerifyOtpRequest(
                    mobileNo = mobile,
                    otp = otp
                )
                val response = OtpRetrofitClient.api.validateOtp(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    val status = body?.response?.status ?: false
                    val message = body?.response?.message ?: "Unknown response"

                    _verifyOtpResult.value = Pair(status, message)
                } else {
                    _verifyOtpResult.value = Pair(false, "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _verifyOtpResult.value = Pair(false, "Exception: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}
