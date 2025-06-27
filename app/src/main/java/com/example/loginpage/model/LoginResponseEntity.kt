package com.example.loginpage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.loginpage.helpers.Converters

@Entity(tableName = "login_response")
@TypeConverters(Converters::class)
data class LoginResponseEntity(
    val nonSbmBilling: Int,
    val status: Boolean,
    val status_code: Int,
    val message: String,
    @PrimaryKey
    val user_id: String,
    val server_date_time: String,
    val software_version_no: String,
    val address: String?,
    val flag: Int,
    val sbm_billing: Int,
    val non_sbm_billing: Int,
    val bill_distribution_flag: Int,
    val quality_check_flag: Int,
    val theft_flag: Int,
    val consumer_fb_flag: Int,
    val extra_conn_flag: Int,
    val bill_flag: Int,
    val account_coll_flag: Int,
    val db_server_user_name: String,
    val db_server_password: String,
    val div_code: String,
    val token: String,
    val module_id: String,
    val software_version_sap: String,
    val urlname: String,
    val apkname: String,
    val versionValidateFlag: String,
    val mobileValidateFlag: String,
    val holidayDateList: List<String>
)
