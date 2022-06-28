package com.example.notificationsapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

object MainViewModel : ViewModel() {
    private val notificationNum = MutableLiveData<Int>()

    val getCount: LiveData<Int> = Transformations.map(notificationNum) { it!! }

    fun setCount(count: Int) { notificationNum.value = count}

    fun incCount() {
        notificationNum.value = notificationNum.value?.plus(1)
    }

    fun decCount() {
        notificationNum.value = notificationNum.value?.minus(1)
    }
}