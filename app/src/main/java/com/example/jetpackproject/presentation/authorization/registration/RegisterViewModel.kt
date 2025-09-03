package com.example.jetpackproject.presentation.authorization.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackproject.data.local.User
import com.example.jetpackproject.data.local.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private val maxChar = 18
    var user = mutableStateOf("")
    var pass = mutableStateOf("")
    var confirm_pass = mutableStateOf("")

    fun userReg(newValue: String) {
        if (newValue.length <= maxChar) {
            user.value = newValue
        }
    }

    fun passReg(newValue: String) {
        if (newValue.length <= maxChar) {
            pass.value = newValue
        }
    }

    fun confPassReg(newValue: String) {
        if (newValue.length <= maxChar) {
            confirm_pass.value = newValue
        }
    }

    fun onRegClicked(userDao: UserDao, context: Context, coroutineScope: CoroutineScope, onSuccess: () -> Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            if (user.value.isEmpty() || pass.value.isEmpty() || confirm_pass.value.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            } else {
                val existingUser = userDao.getUserByUsername(user.value)
                if (existingUser != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Пользователь уже зарегистрирован", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (pass.value != confirm_pass.value) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val newUser = User(username = user.value, password = pass.value)
                        userDao.insertUser(newUser)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                            onSuccess()
                            user.value = ""
                            pass.value = ""
                            confirm_pass.value = ""
                        }
                    }
                }
            }
        }
    }


}