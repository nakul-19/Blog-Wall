package com.nakul.blogWall.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nakul.blogWall.MyApplication
import com.nakul.blogWall.R
import com.nakul.blogWall.models.auth.LoginModel
import com.nakul.blogWall.models.auth.LoginResponse
import com.nakul.blogWall.models.auth.RegisterModel
import com.nakul.blogWall.models.auth.RegisterResponse
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.network.ApiException
import com.nakul.blogWall.network.AuthInterface
import com.nakul.blogWall.repositories.AuthRepository
import com.nakul.blogWall.utils.UtilConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository
    private val sharedPreferences: SharedPreferences

    val signupEvent: LiveData<Event<RegisterResponse>>
    private val _signupEvent: MutableLiveData<Event<RegisterResponse>>

    val signinEvent: LiveData<Event<LoginResponse>>
    private val _signinEvent: MutableLiveData<Event<LoginResponse>>

    init {
        _signupEvent = MutableLiveData()
        signupEvent = _signupEvent
        _signupEvent.value = null
        _signinEvent = MutableLiveData()
        signinEvent = _signinEvent
        _signinEvent.value = null
        repository = AuthRepository(AuthInterface())
        sharedPreferences = application.getSharedPreferences(
            UtilConstants.fileName,
            Context.MODE_PRIVATE
        )
    }

    fun signInUser(email: String, password: String) {
        _signinEvent.postValue(Event.Loading())
        viewModelScope.launch {
            try {
                val res = repository.loginUser(LoginModel(email, password))
                repository.saveUser(sharedPreferences, res)
                _signinEvent.postValue(
                    Event.Success(
                        res,
                        getString(R.string.welcome)
                    )
                )
            } catch (e: Exception) {
                if (e is ApiException)
                    _signinEvent.postValue(Event.Error(e.message.toString()))
                else
                    _signinEvent.postValue(Event.Error(getString(R.string.bad_network)))
            }
            delay(1000L)
            _signupEvent.postValue(null)
        }
    }

    fun signUpUser(email: String, name: String, password: String) {

        _signupEvent.postValue(Event.Loading())

        viewModelScope.launch {
            try {
                val res = repository.registerUser(RegisterModel(email, name, password))
                if (res.sent)
                    _signupEvent.postValue(
                        Event.Success(
                            res,
                            getString(R.string.register_successful)
                        )
                    )
                else
                    throw ApiException(getString(R.string.register_failed))
            } catch (e: Exception) {
                if (e is ApiException)
                    _signupEvent.postValue(Event.Error(e.message.toString()))
                else
                    _signupEvent.postValue(Event.Error(getString(R.string.bad_network)))
            }
            delay(1000L)
            _signupEvent.postValue(null)
        }

    }

    private fun getString(id: Int): String {
        return getApplication<MyApplication>().resources.getString(id)
    }
}