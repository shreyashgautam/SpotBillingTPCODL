package com.example.loginpage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.helpers.AppDatabase
import com.example.loginpage.model.ConsumerRequest
import com.example.loginpage.model.ConsumerResponse
import com.example.loginpage.model.ConsumerResponseEntity
import com.example.loginpage.network.ConsumerRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ConsumerUiState {
    object Idle : ConsumerUiState()
    object Loading : ConsumerUiState()
    data class Success(val data: ConsumerResponse) : ConsumerUiState()
    data class Error(val message: String) : ConsumerUiState()
}

class ConsumerViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<ConsumerUiState>(ConsumerUiState.Idle)
    val uiState: StateFlow<ConsumerUiState> = _uiState

    // DAO instance
    private val dao = AppDatabase.getDatabase(application).conResponseDao()

    fun fetchConsumerData(request: ConsumerRequest) {
        viewModelScope.launch {
            _uiState.value = ConsumerUiState.Loading
            try {
                val response = ConsumerRetrofitClient.apiService.getConsumerBillData(request)
                if (response.isSuccessful) {
                    response.body()?.let { dataList ->
                        val db = AppDatabase.getDatabase(getApplication()) // get database instance
                        db.conResponseDao().deleteAllResponses()
                        // Map API model to Room entity
                        val entityList = dataList.map { response ->
                            ConsumerResponseEntity(
                                KNO = response.KNO,
                                CHK_DGT = response.CHK_DGT,
                                CNSMR_NM = response.CNSMR_NM,
                                MDDL_NM = response.MDDL_NM,
                                LST_NM = response.LST_NM,
                                ADD1 = response.ADD1,
                                ADD2 = response.ADD2,
                                BLL_NO = response.BLL_NO,
                                BLL_MNTH = response.BLL_MNTH,
                                BLL_YR = response.BLL_YR,
                                CRRNT_DMND = response.CRRNT_DMND,
                                CD_PERCENTAGE = response.CD_PERCENTAGE,
                                DUE_AMNT = response.DUE_AMNT,
                                AMNT_PYBL_RNDD = response.AMNT_PYBL_RNDD,
                                DUE30_AMNT = response.DUE30_AMNT,
                                DUE60_AMNT = response.DUE60_AMNT,
                                BLL_DT = response.BLL_DT,
                                DUE_DT = response.DUE_DT,
                                CRCL_CD = response.CRCL_CD,
                                CYCL_NO = response.CYCL_NO,
                                DSTRCT_CD = response.DSTRCT_CD,
                                ZN_CD = response.ZN_CD,
                                INSRTD_DT = response.INSRTD_DT,
                                UPDTD_DT = response.UPDTD_DT,
                                STP_BLL_RSN_CD = response.STP_BLL_RSN_CD,
                                BK_NO = response.BK_NO,
                                CNSMR_TYP_CD = response.CNSMR_TYP_CD,
                                SPPLY_TYP_CD = response.SPPLY_TYP_CD,
                                HRB_FLG = response.HRB_FLG,
                                SLB = response.SLB,
                                LST_ENRGY_PYMNT_AMNT = response.LST_ENRGY_PYMNT_AMNT,
                                CRRNT_RDNG_DT = response.CRRNT_RDNG_DT,
                                SRC_SYSTM = response.SRC_SYSTM,
                                UTILITY_ID = response.UTILITY_ID,
                                NETIA_FLAG = response.NETIA_FLAG,
                                BUSINESS_PRTNR = response.BUSINESS_PRTNR,
                                CNTRCT_ACCNT = response.CNTRCT_ACCNT,
                                BILLNG_CLASS = response.BILLNG_CLASS,
                                CONN_TYP = response.CONN_TYP,
                                MISUSE_TYP = response.MISUSE_TYP,
                                RATE_CATEGORY = response.RATE_CATEGORY,
                                TEMP_CONN_FLG = response.TEMP_CONN_FLG,
                                BL_RBT_DT = response.BL_RBT_DT,
                                BL_RBT_AMT = response.BL_RBT_AMT,
                                MOBILE_NO = response.MOBILE_NO,
                                EMAIL_ID = response.EMAIL_ID,
                                DISTRICT_CODE = response.DISTRICT_CODE,
                                CONSUMER_TYPE = response.CONSUMER_TYPE,
                                LEGACY_KNO = response.LEGACY_KNO,
                                DN_No = response.DN_No,
                                DN_SD = response.DN_SD,
                                DN_Total = response.DN_Total,
                                Payable_Amt = response.Payable_Amt,
                                srvdatetime = response.srvdatetime,
                                julandt = response.julandt,
                                Status = response.Status,
                                Message = response.Message,
                                USER_ID = response.USER_ID,
                                RECORD_STATUS = response.RECORD_STATUS,
                                MAXIMUM_AMOUNT = response.MAXIMUM_AMOUNT,
                                ChequeAllowed = response.ChequeAllowed,
                                COLL_Version = response.COLL_Version,
                                Bill_Version = response.Bill_Version,
                                AndroidID = response.AndroidID,
                                VersionFlag = response.VersionFlag,
                                DeviceFlag = response.DeviceFlag,
                                ISSUE_TO = response.ISSUE_TO,
                                COLL_Url = response.COLL_Url,
                                BILL_Url = response.BILL_Url,
                                Digital_Rebate = response.Digital_Rebate,
                                IsPrepaid = response.IsPrepaid,
                                PP_SIM_BILL_BIS = response.PP_SIM_BILL_BIS,
                                PP_SIM_NILL_AB = response.PP_SIM_NILL_AB,
                                PP_NET_BAL = response.PP_NET_BAL,
                                PP_Vkont = response.PP_Vkont,
                                PP_CONSUMPTION = response.PP_CONSUMPTION,
                                PP_SPLY_STTS = response.PP_SPLY_STTS,
                                PP_THRSHLD_BAL = response.PP_THRSHLD_BAL,
                                LIMIT_CHECK = response.LIMIT_CHECK,
                                MINIMUM_AMNT = response.MINIMUM_AMNT,
                                NOTPAID_CNT = response.NOTPAID_CNT,
                                LAST_PAYMENT_AMOUNT = response.LAST_PAYMENT_AMOUNT,
                                LAST_PAYMENT_DATE = response.LAST_PAYMENT_DATE,
                                DAY_CASH_AMOUNT = response.DAY_CASH_AMOUNT,
                                SYS_Payable_Amt = response.SYS_Payable_Amt,
                                PYMNT_AFT_D_RBT = response.PYMNT_AFT_D_RBT,
                                SEC_MOBILE_NO = response.SEC_MOBILE_NO,
                                Classification = response.Classification,
                                ECL_AMT = response.ECL_AMT
                            )
                        }

                        // Insert all entities to Room DB
                        dao.insertAll(entityList)

                        // Update UI with original API data
                        _uiState.value = ConsumerUiState.Success(dataList)
                    } ?: run {
                        _uiState.value = ConsumerUiState.Error("Empty response body")
                    }
                } else {
                    _uiState.value = ConsumerUiState.Error("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = ConsumerUiState.Error("Exception: ${e.localizedMessage}")
            }
        }
    }

    // Optional: Flow for observing all saved consumer responses from DB
    val allConsumerData = dao.getAllResponses()
}
