package com.example.loginpage.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.model.LoginRequest
import com.example.loginpage.model.LoginResponse
import com.example.loginpage.model.LoginResponseEntity
import com.example.loginpage.network.RetrofitClient
import com.example.loginpage.session.SessionManager
import com.example.loginpage.helpers.DatabaseBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// UI state representation
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    // New StateFlow to hold all saved login responses from Room DB
    private val _dbResponses = MutableStateFlow<List<LoginResponseEntity>>(emptyList())
    val dbResponses: StateFlow<List<LoginResponseEntity>> = _dbResponses
    fun clearLoginData(context: Context) {
        viewModelScope.launch {
            val db = DatabaseBuilder.getInstance(context)
            db.loginResponseDao().deleteAll()
            SessionManager(context).clearSession()
            _loginState.value = LoginState.Idle
            _dbResponses.value = emptyList()
        }
    }

    fun login(userId: String, password: String, context: Context) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val request = LoginRequest(
                    user_id = userId,
                    password = password,
                    type = "",
                    moduleId = ""
                )

                val response = RetrofitClient.api.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        // Save session token for 10 days
                        SessionManager(context).saveSession(body.token)

                        // Insert into Room database
                        val db = DatabaseBuilder.getInstance(context)
                        val entity = LoginResponseEntity(
                            nonSbmBilling = body.nonSbmBilling,
                            status = body.status,
                            status_code = body.status_code,
                            message = body.message,
                            user_id = body.user_id,
                            server_date_time = body.server_date_time,
                            software_version_no = body.software_version_no,
                            address = body.address,
                            flag = body.flag,
                            sbm_billing = body.sbm_billing,
                            non_sbm_billing = body.non_sbm_billing,
                            bill_distribution_flag = body.bill_distribution_flag,
                            quality_check_flag = body.quality_check_flag,
                            theft_flag = body.theft_flag,
                            consumer_fb_flag = body.consumer_fb_flag,
                            extra_conn_flag = body.extra_conn_flag,
                            bill_flag = body.bill_flag,
                            account_coll_flag = body.account_coll_flag,
                            db_server_user_name = body.db_server_user_name,
                            db_server_password = body.db_server_password,
                            div_code = body.div_code,
                            token = body.token,
                            module_id = body.module_id,
                            software_version_sap = body.software_version_sap,
                            urlname = body.urlname,
                            apkname = body.apkname,
                            versionValidateFlag = body.versionValidateFlag,
                            mobileValidateFlag = body.mobileValidateFlag,
                            holidayDateList = body.holidayDateList
                        )
                        db.loginResponseDao().insertLoginResponse(entity)

                        _loginState.value = LoginState.Success(body)
                    } else {
                        _loginState.value = LoginState.Error(body?.message ?: "Invalid credentials")
                    }
                } else {
                    _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                }

            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Exception: ${e.localizedMessage}")
            }
        }
    }

    // New function: load all saved login responses from Room DB
    fun loadFromDatabase(context: Context) {
        viewModelScope.launch {
            val db = DatabaseBuilder.getInstance(context)
            val allResponses = db.loginResponseDao().getAllLoginResponses()
            _dbResponses.value = allResponses
        }
    }
}
