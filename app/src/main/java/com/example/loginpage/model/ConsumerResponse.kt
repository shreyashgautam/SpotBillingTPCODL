package com.example.loginpage.model


typealias ConsumerResponse = List<ConResponse>;

data class ConResponse(
    val KNO: String?,
    val CHK_DGT: String?,
    val CNSMR_NM: String?,
    val MDDL_NM: String?,
    val LST_NM: String?,
    val ADD1: String?,
    val ADD2: String?,
    val BLL_NO: String?,
    val BLL_MNTH: String?,
    val BLL_YR: String?,
    val CRRNT_DMND: String?,
    val CD_PERCENTAGE: String?,
    val DUE_AMNT: String?,
    val AMNT_PYBL_RNDD: String?,
    val DUE30_AMNT: String?,
    val DUE60_AMNT: String?,
    val BLL_DT: String?,
    val DUE_DT: String?,
    val CRCL_CD: String?,
    val CYCL_NO: String?,
    val DSTRCT_CD: String?,
    val ZN_CD: String?,
    val INSRTD_DT: String?,
    val UPDTD_DT: String?,
    val STP_BLL_RSN_CD: String?,
    val BK_NO: String?,
    val CNSMR_TYP_CD: String?,
    val SPPLY_TYP_CD: String?,
    val HRB_FLG: String?,
    val SLB: String?,
    val LST_ENRGY_PYMNT_AMNT: String?,
    val CRRNT_RDNG_DT: String?,
    val SRC_SYSTM: String?,
    val UTILITY_ID: String?,
    val NETIA_FLAG: String?,
    val BUSINESS_PRTNR: String?,
    val CNTRCT_ACCNT: String?,
    val BILLNG_CLASS: String?,
    val CONN_TYP: String?,
    val MISUSE_TYP: String?,
    val RATE_CATEGORY: String?,
    val TEMP_CONN_FLG: String?,
    val BL_RBT_DT: String?,
    val BL_RBT_AMT: String?,
    val MOBILE_NO: String?,
    val EMAIL_ID: String?,
    val DISTRICT_CODE: String?,
    val CONSUMER_TYPE: String?,
    val LEGACY_KNO: String?,
    val DN_No: String?,
    val DN_SD: String?,
    val DN_Total: String?,
    val Payable_Amt: String?,
    val srvdatetime: String?,
    val julandt: String?,
    val Status: String?,
    val Message: String?,
    val USER_ID: String?,
    val RECORD_STATUS: String?,
    val MAXIMUM_AMOUNT: String?,
    val ChequeAllowed: String?,
    val COLL_Version: String?,
    val Bill_Version: String?,
    val AndroidID: String?,
    val VersionFlag: String?,
    val DeviceFlag: String?,
    val ISSUE_TO: String?,
    val COLL_Url: String?,
    val BILL_Url: String?,
    val Digital_Rebate: String?,
    val IsPrepaid: String?,
    val PP_SIM_BILL_BIS: String?,
    val PP_SIM_NILL_AB: String?,
    val PP_NET_BAL: String?,
    val PP_Vkont: String?,
    val PP_CONSUMPTION: String?,
    val PP_SPLY_STTS: String?,
    val PP_THRSHLD_BAL: String?,
    val LIMIT_CHECK: String?,
    val MINIMUM_AMNT: String?,
    val NOTPAID_CNT: String?,
    val LAST_PAYMENT_AMOUNT: String?,
    val LAST_PAYMENT_DATE: String?,
    val DAY_CASH_AMOUNT: String?,
    val SYS_Payable_Amt: String?,
    val PYMNT_AFT_D_RBT: String?,
    val SEC_MOBILE_NO: String?,
    val Classification: String?,
    val ECL_AMT: String?
)
