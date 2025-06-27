package com.example.loginpage

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.loginpage.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardUser(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val dbResponses by viewModel.dbResponses.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadFromDatabase(context)
    }

    val userName = dbResponses.firstOrNull()?.db_server_user_name ?: "User"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Dashboard", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A148C)),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(userName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Icon",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                navController.navigate("home") {
                                    popUpTo("dashboard") { inclusive = true }
                                }
                            },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Go to Home", color = Color(0xFF4A148C), fontWeight = FontWeight.Medium)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Arrow",
                                        tint = Color(0xFF4A148C)
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (dbResponses.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No user data in database", fontSize = 18.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                items(dbResponses) { data ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF3E5F5)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                "User ID: ${data.user_id}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A148C)
                            )
                            Spacer(Modifier.height(16.dp))

                            DataRow("Division Code", data.div_code)
                            DataRow("Address", data.address ?: "N/A")
                            DataRow("DB Username", data.db_server_user_name ?: "N/A")
                            DataRow("Software Version", data.software_version_no)
                            DataRow("SAP Version", data.software_version_sap)
                            DataRow("Server DateTime", data.server_date_time)
                            DataRow("Module ID", data.module_id)
                            DataRow("Version Validate", if (data.versionValidateFlag == "1") "Yes" else "No")
                            DataRow("Mobile Validate", if (data.mobileValidateFlag == "1") "Yes" else "No")
                            DataRow("Flag", if (data.flag == 1) "Yes" else "No")
                            DataRow("SBM Billing", if (data.sbm_billing == 1) "Yes" else "No")
                            DataRow("Non SBM Billing", if (data.nonSbmBilling == 1) "Yes" else "No")
                            DataRow("Non SBM Billing (alt)", if (data.non_sbm_billing == 1) "Yes" else "No")
                            DataRow("Bill Distribution", if (data.bill_distribution_flag == 1) "Yes" else "No")
                            DataRow("Quality Check", if (data.quality_check_flag == 1) "Yes" else "No")
                            DataRow("Theft", if (data.theft_flag == 1) "Yes" else "No")
                            DataRow("Consumer Feedback", if (data.consumer_fb_flag == 1) "Yes" else "No")
                            DataRow("Extra Connection", if (data.extra_conn_flag == 1) "Yes" else "No")
                            DataRow("Bill Flag", if (data.bill_flag == 1) "Yes" else "No")
                            DataRow("Account Collection", if (data.account_coll_flag == 1) "Yes" else "No")

                            if (data.holidayDateList.isNotEmpty()) {
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    "Holiday Dates:",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF4A148C)
                                )
                                Spacer(Modifier.height(8.dp))
                                data.holidayDateList.forEach {
                                    Text("- $it", fontSize = 15.sp, color = Color.Black, modifier = Modifier.padding(bottom = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6A1B9A) // Purple-ish color for label
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = Color(0xFFE1BEE7),
            thickness = 1.dp
        )
    }
}
