package kbs.apps.mobiledevelopment.week11database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel: ViewModel() {
    var number = 0
    val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val currentBoolean: MutableLiveData<Boolean> by lazy{
        MutableLiveData<Boolean>()
    }
}