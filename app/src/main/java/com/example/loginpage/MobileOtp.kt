package com.example.loginpage

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.loginpage.viewmodel.MobileOtpViewModel
import kotlinx.coroutines.delay

@Composable
fun MobileOtp(
    navController: NavController,
    viewModel: MobileOtpViewModel = viewModel()
) {
    val context = LocalContext.current

    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var showOtpDialog by remember { mutableStateOf(false) }
    var isOtpSent by remember { mutableStateOf(false) }
    var canResendOtp by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(60) }

    var sendOtpMessage by remember { mutableStateOf<String?>(null) }
    var verifyOtpMessage by remember { mutableStateOf<String?>(null) }

    val isEmailValid = remember(email) { Patterns.EMAIL_ADDRESS.matcher(email).matches() }
    val isPhoneValid = remember(phoneNumber) {
        phoneNumber.length == 10 && phoneNumber.all { it.isDigit() } &&
                (phoneNumber.startsWith("9") || phoneNumber.startsWith("8")
                        || phoneNumber.startsWith("7") || phoneNumber.startsWith("6"))
    }

    val sendOtpResult by viewModel.sendOtpResult.collectAsState()
    val verifyOtpResult by viewModel.verifyOtpResult.collectAsState()

    val otpDigits = remember { List(6) { mutableStateOf("") } }
    val focusRequesters = List(6) { FocusRequester() }
    val otp = otpDigits.joinToString("") { it.value }

    LaunchedEffect(isOtpSent, showOtpDialog) {
        if (isOtpSent && showOtpDialog) {
            canResendOtp = false
            timer = 60
            while (timer > 0) {
                delay(1000)
                timer--
            }
            canResendOtp = true
        }
    }

    LaunchedEffect(sendOtpResult) {
        sendOtpResult?.let { (success, message) ->
            sendOtpMessage = message
            if (success) {
                showOtpDialog = true
                isOtpSent = true
            }
            else{
                showOtpDialog = true
                isOtpSent = true
            }
        }
    }

    LaunchedEffect(verifyOtpResult) {
        verifyOtpResult?.let { (success, message) ->
            verifyOtpMessage = message
            if (success) {
                showOtpDialog = false
                isOtpSent = false
                otpDigits.forEach { it.value = "" }
                navController.navigate("login2") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC11111)) // Dark transparent overlay
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login with OTP", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                isError = email.isNotBlank() && !isEmailValid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    errorBorderColor = Color.Red
                )
            )
            if (email.isNotBlank() && !isEmailValid) {
                Text("Enter a valid email address", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 10 && it.all { c -> c.isDigit() }) phoneNumber = it
                },
                label = { Text("Mobile Number", color = Color.White) },
                isError = phoneNumber.isNotBlank() && !isPhoneValid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    errorBorderColor = Color.Red
                )
            )
            if (phoneNumber.isNotBlank() && !isPhoneValid) {
                Text("Enter a valid 10-digit mobile number", color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.sendOtp(email, phoneNumber) },
                enabled = isEmailValid && isPhoneValid,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4),
                    contentColor = Color.White
                )
            ) {
                Text("Send OTP")
            }

            sendOtpMessage?.let {
                Text(text = it, color = if (it.contains("success", true)) Color.Green else Color.Red)
            }
        }

        if (showOtpDialog) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        "Enter OTP",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(7.dp),

                        ) {
                            otpDigits.forEachIndexed { index, digit ->
                                OutlinedTextField(
                                    value = digit.value,
                                    onValueChange = { value ->
                                        if (value.length <= 1 && value.all { it.isDigit() }) {
                                            digit.value = value
                                            if (value.isNotEmpty() && index < 5) {
                                                focusRequesters[index + 1].requestFocus()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .width(39.dp)
                                        .height(50.dp)
                                        .focusRequester(focusRequesters[index]),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    ),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = Color.Gray,
                                        cursorColor = MaterialTheme.colorScheme.primary,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!canResendOtp) {
                            Text(
                                text = "You can resend OTP in $timer seconds",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = {
                                    if (canResendOtp) {
                                        otpDigits.forEach { it.value = "" }
                                        isOtpSent = true
                                        viewModel.sendOtp(email, phoneNumber)
                                    }
                                },
                                enabled = canResendOtp
                            ) {
                                Text("Reset OTP", color = if (canResendOtp) MaterialTheme.colorScheme.primary else Color.Gray)
                            }

                            TextButton(onClick = {
                                showOtpDialog = false
                                isOtpSent = false
                            }) {
                                Text("Cancel")
                            }

                            Button(
                                onClick = {
                                    viewModel.verifyOtp(phoneNumber, otp)
                                },
                                enabled = otp.length == 6
                            ) {
                                Text("Verify")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        verifyOtpMessage?.let {
                            Text(
                                text = it,
                                color = if (it.contains("success", true) || it.contains("verified", true)) Color.Green else Color.Red,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {},
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.widthIn(min = 300.dp, max = 360.dp)
            )
        }
    }
}
