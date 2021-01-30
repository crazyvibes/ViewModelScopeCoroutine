package `in`.bk.viewmodelscopecoroutine

import `in`.bk.viewmodelscopecoroutine.model.User
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainActivityViewModel:ViewModel() {
//    private val myJob= Job()
//    private val myScope= CoroutineScope(Dispatchers.IO+myJob)

//    fun getUserData(){
//        myScope.launch {
//
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        myJob.cancel()
//    }


    private val userRepository=UserRepository()


    //ViewModelScope
//    var users:MutableLiveData<List<User>> = MutableLiveData()
//    fun getUserData(){
//        viewModelScope.launch {
//            var result:List<User>?=null
//            withContext(Dispatchers.IO){
//                result=userRepository.getUser()
//            }
//            users.value=result
//        }
//
//    }

    //liveDataBuilder
    var users = liveData(Dispatchers.IO) {
        val result =userRepository.getUser()
        emit(result)
    }

}


/**So,To cancel all the corotuiens launched in this scope, only thing we need to do is this.
Canceling corouitnes manually

-like we just did, might be useful in some situations. But think, IF we have 20 viewmodel classes in our app,
doing this manually might be unnecessary wasting of time.

-So, to avoid that, to avoid those boiler plate codes we can use viewModelScope.

-This new viewModelScope is bounded to ViewModel’s lifecycle. It has created to automatically handle cancellation
when the ViewModel’s onClear() is called.

-We can easily use this scope from an extension function available on the viewmodel-ktx library.*/
