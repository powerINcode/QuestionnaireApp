package com.powerincode.questionnaire_app.screens._base.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class StateViewModel<TState : Any, TNavigation : Any> : BaseViewModel() {
    protected val _state : MutableLiveData<TState> = MutableLiveData()
    val state : LiveData<TState> = _state

    protected val _navigation : MutableLiveData<TNavigation> = MutableLiveData()
    val navigation : LiveData<TNavigation> = _navigation
}