package com.stevdza.san.mongodemo.screen.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stevdza.san.mongodemo.R
import com.stevdza.san.mongodemo.screen.AuthVm
import com.stevdza.san.mongodemo.ui.Constants.OBJECT_ID_AUTH
import com.stevdza.san.mongodemo.ui.theme.DarkBlue
import com.stevdza.san.mongodemo.ui.theme.button
import com.stevdza.san.mongodemo.ui.theme.h2


@Composable
fun AuthKeyScreen(
    navToHomeScreen:()-> Unit
){

    val authKeyVm: AuthVm = viewModel()
    var accessKey by remember { mutableStateOf("") }
    var newAccessKey by remember { mutableStateOf("") }
    var confirmNewAccessKey by remember { mutableStateOf("") }


    var sellerKey by remember { mutableStateOf("") }
    var newSellerKey by remember { mutableStateOf("") }
    var confirmNewSellerKey by remember { mutableStateOf("") }


//    var a by remember { mutableStateOf("") }
//    var b by remember { mutableStateOf("") }
//
//    authKeyVm.sellerKey.value = b
//    authKeyVm.accessKey.value = a
//
//    val date = authKeyVm.data
//
//    date.value.forEach {
//        Log.e(TAG, "AuthKeyScreen: ${it._id.toHexString()}", )
//    }

    val objectId by remember { mutableStateOf(OBJECT_ID_AUTH) }
    val date = authKeyVm.authById

    val accessKeyCheck = date.value?.accessKey
    val sellerKeyCheck = date.value?.sellerKey

    authKeyVm.objectId.value = objectId

    LaunchedEffect(key1 = true ){
        authKeyVm.getOrderListById()
    }

    authKeyVm.sellerKey.value = confirmNewSellerKey
    authKeyVm.accessKey.value = confirmNewAccessKey

    Log.e(TAG, "accessKeyOne: ${date.value?.accessKey}", )
    Log.e(TAG, "sellerKeyOne: ${date.value?.sellerKey}", )

    AccessKeyScreen(
        accessKey = accessKey,
        onAccessKeyChange = {accessKey=it},
        newAccessKey = newAccessKey,
        onNewAccessKeyChange = {newAccessKey=it},
        confirmNewAccessKey = confirmNewAccessKey,
        onConfirmNewAccessKeyChange ={confirmNewAccessKey=it} ,
        onSaveAccessKeyClick = {
//            authKeyVm.insertAuth()
            if (accessKey == accessKeyCheck && accessKey.isNotEmpty()){
                if (sellerKeyCheck != null) {
                    authKeyVm.sellerKey.value = sellerKeyCheck
                }
                if (newAccessKey == confirmNewAccessKey && newAccessKey.isNotEmpty() && confirmNewAccessKey.isNotEmpty()){
                    authKeyVm.accessKey.value = confirmNewAccessKey
                    authKeyVm.updateAuth()
                    navToHomeScreen()
                }else{
                    Log.e(TAG, "AuthKeyScreen: passowrd doesn't march", )
                }
            }else{
                Log.e(TAG, "AuthKeyScreen: wrong old password or empty fill", )
            }
            Log.e(TAG, "accessKey: ${date.value?.accessKey}", )

        },
        sellerKey = sellerKey,
        onSellerKeyChange = {sellerKey=it},
        newSellerKey = newSellerKey,
        onNewSellerKeyChange = {newSellerKey=it},
        confirmNewSellerKey = confirmNewSellerKey,
        onConfirmNewSellerKeyChange = {confirmNewSellerKey=it},
        onSaveSellerKeyClick = {
            if (sellerKey == sellerKeyCheck && sellerKey.isNotEmpty()){
                if (accessKeyCheck != null) {
                    authKeyVm.accessKey.value = accessKeyCheck
                }
                if (newSellerKey == confirmNewSellerKey && newSellerKey.isNotEmpty() && confirmNewSellerKey.isNotEmpty()){
                    authKeyVm.sellerKey.value = confirmNewSellerKey
                    authKeyVm.updateAuth()
                    navToHomeScreen()
                }else{
                    Log.e(TAG, "AuthKeyScreen: passowrd doesn't march", )
                }
            }else{
                Log.e(TAG, "AuthKeyScreen: wrong old password or empty fill", )
            }
            Log.e(TAG, "accessKeyCheck: $accessKeyCheck", )
        },
//        accessKey2 = a,
//        onAccessKey2Change = {a=it },
//        newAccessKey2 = b,
//        onNewAccessKey2Change = {b=it}
    )
    Log.e(TAG, "sellerKey: ${authKeyVm.sellerKey.value}", )
    Log.e(TAG, "accessKey: ${authKeyVm.accessKey.value}", )

}

@Composable
fun AccessKeyScreen(
    accessKey: String,
    onAccessKeyChange: (String)-> Unit,
    newAccessKey: String,
    onNewAccessKeyChange: (String)-> Unit,
    confirmNewAccessKey: String,
    onConfirmNewAccessKeyChange: (String)-> Unit,
    onSaveAccessKeyClick: ()-> Unit,
    sellerKey: String,
    onSellerKeyChange: (String)-> Unit,
    newSellerKey: String,
    onNewSellerKeyChange: (String)-> Unit,
    confirmNewSellerKey: String,
    onConfirmNewSellerKeyChange: (String)-> Unit,
    onSaveSellerKeyClick: ()-> Unit,
//    accessKey2: String,
//    onAccessKey2Change: (String)-> Unit,
//    onNewAccessKey2Change: (String)-> Unit,
//    newAccessKey2: String,

){
    LazyColumn{
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

//        OutlinedTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .padding(horizontal = 30.dp),
//            value = accessKey2,
//            onValueChange = onAccessKey2Change,
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.White
//            ),
//            shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
//            singleLine = true,
//            textStyle = button,
//            label = { Text(text = "Old Access Key") },
//            placeholder = { Text(text = "Old Access Key") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//        )
//
//        Spacer(modifier = Modifier.padding(10.dp))
//        OutlinedTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .padding(horizontal = 30.dp),
//            value = newAccessKey2,
//            onValueChange = onNewAccessKey2Change,
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.White
//            ),
//            shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
//            singleLine = true,
//            textStyle = button,
//            label = { Text(text = "New Access Key") },
//            placeholder = { Text(text = "New Access Key") },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//        )
//        Spacer(modifier = Modifier.padding(10.dp))

                var oldPasswordVisibility by remember { mutableStateOf(false) }
                var newPasswordVisibility by remember { mutableStateOf(false) }
                var comfirmNewPasswordVisibility by remember { mutableStateOf(false) }

                var oldPasswordSellerVisibility by remember { mutableStateOf(false) }
                var newPasswordSellerVisibility by remember { mutableStateOf(false) }
                var comfirmNewPasswordSellerVisibility by remember { mutableStateOf(false) }
                var resetAccessKey by remember { mutableStateOf(false) }
                var resetSellerKey by remember { mutableStateOf(false) }
                val rotationState by animateFloatAsState(
                    targetValue = if (resetAccessKey) 180f else 0f)
                val rotationSellerState by animateFloatAsState(
                    targetValue = if (resetSellerKey) 180f else 0f)
                Card(
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Card(

                            shape = RoundedCornerShape(10.dp),
                            elevation = 10.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        resetAccessKey = !resetAccessKey
//                hide = !hide
                                    }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(8f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Rest Access Key", style = h2)
                                }
                                IconButton(
                                    modifier = Modifier
                                        .weight(2f)
                                        .alpha(ContentAlpha.medium)
                                        .rotate(rotationState),
                                    onClick = { resetAccessKey = !resetAccessKey }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Drop-Down Arrow"
                                    )
                                }
                            }
                        }

                        if (resetAccessKey){
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = accessKey,
                                onValueChange = onAccessKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "Old Access Key") },
                                placeholder = { Text(text = "Old Access Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        oldPasswordVisibility = !oldPasswordVisibility
                                    }) {
                                        Icon(
                                            painter = if (oldPasswordVisibility) painterResource(id = R.drawable.visibility_off) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }
                                },
                                visualTransformation = if (oldPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            Spacer(modifier = Modifier.padding(10.dp))
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = newAccessKey,
                                onValueChange = onNewAccessKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "New Access Key") },
                                placeholder = { Text(text = "New Access Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        newPasswordVisibility = !newPasswordVisibility
                                    }) {
                                        Icon(
                                            painter = if (newPasswordVisibility) painterResource(id = R.drawable.visibility_off) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }

                                },
                                visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = confirmNewAccessKey,
                                onValueChange = onConfirmNewAccessKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "Confirm New Access Key") },
                                placeholder = { Text(text = "Confirm New Access Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        comfirmNewPasswordVisibility = !comfirmNewPasswordVisibility
                                    }) {
                                        Icon(
                                            painter = if (comfirmNewPasswordVisibility) painterResource(
                                                id = R.drawable.visibility_off
                                            ) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }

                                },
                                visualTransformation = if (comfirmNewPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(horizontal = 30.dp),
                                shape = RoundedCornerShape(20.dp),
                                onClick = { onSaveAccessKeyClick() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = DarkBlue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Done", style = button,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }

                    }




                }
                Spacer(modifier = Modifier.padding(10.dp))

                Card(
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            elevation = 10.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        resetSellerKey = !resetSellerKey
//                hide = !hide
                                    }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(8f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Rest Seller Key", style = h2)
                                }
                                IconButton(
                                    modifier = Modifier
                                        .weight(2f)
                                        .alpha(ContentAlpha.medium)
                                        .rotate(rotationSellerState),
                                    onClick = { resetSellerKey = !resetSellerKey }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Drop-Down Arrow"
                                    )
                                }
                            }
                        }

                        if (resetSellerKey) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = sellerKey,
                                onValueChange = onSellerKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "Old Seller Key") },
                                placeholder = { Text(text = "Old Seller Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        oldPasswordSellerVisibility = !oldPasswordSellerVisibility
                                    }) {
                                        Icon(
                                            painter = if (oldPasswordSellerVisibility) painterResource(id = R.drawable.visibility_off) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }

                                },
                                visualTransformation = if (oldPasswordSellerVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )

                            Spacer(modifier = Modifier.padding(10.dp))

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = newSellerKey,
                                onValueChange = onNewSellerKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "New Access Key") },
                                placeholder = { Text(text = "New Access Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        newPasswordSellerVisibility = !newPasswordSellerVisibility
                                    }) {
                                        Icon(
                                            painter = if (newPasswordSellerVisibility) painterResource(id = R.drawable.visibility_off) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }

                                },
                                visualTransformation = if (newPasswordSellerVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))

                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(horizontal = 30.dp),
                                value = confirmNewSellerKey,
                                onValueChange = onConfirmNewSellerKeyChange,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White
                                ),
                                shape = RoundedCornerShape(topEnd = 10.dp, bottomStart = 10.dp),
                                singleLine = true,
                                textStyle = button,
                                label = { Text(text = "Confirm New Seller Key") },
                                placeholder = { Text(text = "Confirm New Seller Key") },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        comfirmNewPasswordSellerVisibility =
                                            !comfirmNewPasswordSellerVisibility
                                    }) {
                                        Icon(
                                            painter = if (comfirmNewPasswordSellerVisibility) painterResource(
                                                id = R.drawable.visibility_off
                                            ) else painterResource(
                                                id = R.drawable.visibility
                                            ),
                                            contentDescription = "visibility",
                                            tint = Color.Black
                                        )
                                    }

                                },
                                visualTransformation = if (comfirmNewPasswordSellerVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(horizontal = 30.dp),
                                shape = RoundedCornerShape(20.dp),
                                onClick = { onSaveSellerKeyClick() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = DarkBlue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Done", style = button,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                    }
                }

            }
        }
    }


}

