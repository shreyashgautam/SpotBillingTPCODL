package com.example.loginpage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.api.RetrofitInstance
import com.example.loginpage.model.SpotBillingRequest
import com.example.loginpage.model.SpotBillingResponse
import com.example.loginpage.network.RetrofitClient
import com.example.loginpage.network.SpotBillingApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpotBillingViewModel : ViewModel() {

    companion object {
        private const val TAG = "SpotBillingViewModel"
    }

    private val _spotBillingData = MutableStateFlow<SpotBillingResponse?>(null)
    val spotBillingData: StateFlow<SpotBillingResponse?> = _spotBillingData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchSpotBillingData(request: SpotBillingRequest, token: String) {
        Log.d(TAG, "Fetching spot billing data with user_id: ${request.user_id}")

        val jsonObject=JsonObject()
        jsonObject.addProperty("user_id","9876500205");
        jsonObject.addProperty("type","b");
        jsonObject.addProperty("consumer_type","S");
        jsonObject.addProperty("search_flag","S");
        jsonObject.addProperty("version","1.0.0");

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                Log.d(TAG, "Creating API service with token: $token")
                val apiService = RetrofitInstance.getRetrofitInstance { token }


              //  val response = RetrofitClient.api.login(request)

                Log.d(TAG, "Making network request...")

                val response = apiService.getSpotBillingData("Bearer $token",jsonObject)
                if (response.isSuccessful && response.body() != null) {
                    _spotBillingData.value = response.body()

                    Log.i(TAG, "API Success: ${response.body()?.message} ${request.user_id} ${request.type} ${request.consumer_type} ${request.version} ${request.search_flag}")
                } else {
                    val errMsg = "API Error: ${response.code()} ${response.message()} ${token} ${request.user_id} ${request.type} ${request.consumer_type} ${request.version} ${request.search_flag}"
                    _error.value = errMsg
                    Log.e(TAG, errMsg )
                }
            } catch (e: Exception) {
                val exceptionMsg = "Exception during API call: ${e.localizedMessage ?: "Unknown error"}"
                _error.value = exceptionMsg
                Log.e(TAG, exceptionMsg, e)
            } finally {
                _isLoading.value = false
                Log.d(TAG, "Loading completed")
            }
        }
    }
}
