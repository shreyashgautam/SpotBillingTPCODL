package com.example.loginpage

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.loginpage.helpers.AppDatabase
import com.example.loginpage.model.ConsumerRequest
import com.example.loginpage.model.ConsumerResponseEntity
import com.example.loginpage.session.SessionManager
import com.example.loginpage.viewmodel.ConsumerUiState
import com.example.loginpage.viewmodel.ConsumerViewModel
import com.example.loginpage.viewmodel.LoginState
import com.example.loginpage.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    consumerViewModel: ConsumerViewModel = viewModel(),
    context: Context
) {
    val loginState by viewModel.loginState.collectAsState()
    val uiState by consumerViewModel.uiState.collectAsState()
    val allConsumers by consumerViewModel.allConsumerData.collectAsState(initial = emptyList())

    val userName = when (val state = loginState) {
        is LoginState.Success -> state.data.db_server_user_name ?: "Unknown User"
        else -> "Unknown User"
    }

    val userDetails = when (val state = loginState) {
        is LoginState.Success -> state.data
        else -> null
    }

    val sessionManager = remember { SessionManager(context) }
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var consumerNumber by remember { mutableStateOf(TextFieldValue("")) }
    var billType by remember { mutableStateOf(TextFieldValue("")) }
    var userId by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome To TPCODL", color = Color.White, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE)),
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text("Username: $userName", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clickable {
                                                expanded = false
                                                navController.navigate("dashboard")
                                            }
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text("Go to Dashboard", color = Color(0xFF6200EE), fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(Icons.Default.ArrowForward, contentDescription = "Go", tint = Color(0xFF6200EE))
                                    }
                                    userDetails?.div_code?.let {
                                        Text("Division Code: $it", fontSize = 14.sp, color = Color.Gray)
                                    }
                                    userDetails?.software_version_no?.let {
                                        Text("Version: $it", fontSize = 14.sp, color = Color.Gray)
                                    }
                                }
                            },
                            onClick = { expanded = false }
                        )
                    }

                    IconButton(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val db = AppDatabase.getDatabase(context)
                            db.conResponseDao().deleteAllResponses()
                            withContext(Dispatchers.IO) {
                                viewModel.clearLoginData(context)
                            }
                            sessionManager.clearSession()
                            withContext(Dispatchers.Main) {
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = consumerNumber,
                onValueChange = { consumerNumber = it },
                label = { Text("Consumer Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = billType,
                onValueChange = { billType = it },
                label = { Text("Bill Type") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val request = ConsumerRequest(
                        ConsumerNumber = consumerNumber.text,
                        bill_type = billType.text,
                        usrid = userId.text,
                        ref_no = "0"
                    )
                    consumerViewModel.fetchConsumerData(request)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Fetch Consumer Data")
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (uiState) {
                is ConsumerUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ConsumerUiState.Error -> {
                    Text(
                        text = (uiState as ConsumerUiState.Error).message,
                        color = Color.Red
                    )
                }
                else -> {}
            }

            if (allConsumers.isNotEmpty()) {
                Text("Fetched Consumer Records:", fontSize = 18.sp, color = Color(0xFF6200EE))
                Spacer(modifier = Modifier.height(8.dp))

                allConsumers.forEach { consumer ->
                    ConsumerCard(consumer)
                }
            } else {
                Text("No consumer data found.", color = Color.Gray)
            }
        }
    }
}
@Composable
fun ConsumerCard(consumer: ConsumerResponseEntity) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Consumer Details",
                fontSize = 20.sp,
                color = Color(0xFF6200EE),
                style = MaterialTheme.typography.titleMedium
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

            VerticalDetailIfNotEmpty("KNO", consumer.KNO)
            VerticalDetailIfNotEmpty("Name", consumer.CNSMR_NM)
            VerticalDetailIfNotEmpty("Address 1", consumer.ADD1)
            VerticalDetailIfNotEmpty("Address 2", consumer.ADD2)
            VerticalDetailIfNotEmpty("Mobile", consumer.MOBILE_NO)
            VerticalDetailIfNotEmpty("Email", consumer.EMAIL_ID)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Billing Info",
                fontSize = 18.sp,
                color = Color(0xFF6200EE),
                style = MaterialTheme.typography.titleSmall
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

            VerticalDetailIfNotEmpty("Due Amount", consumer.DUE_AMNT?.let { "₹$it" })
            VerticalDetailIfNotEmpty("Bill Date", consumer.BLL_DT)
            VerticalDetailIfNotEmpty("Due Date", consumer.DUE_DT)
        }
    }
}


@Composable
fun VerticalDetailIfNotEmpty(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Column(modifier = Modifier.padding(vertical = 6.dp)) {
            Text(
                text = "$label:",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black,

            )
        }
    }
}

