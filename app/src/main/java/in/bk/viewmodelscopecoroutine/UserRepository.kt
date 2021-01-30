package `in`.bk.viewmodelscopecoroutine

import `in`.bk.viewmodelscopecoroutine.model.User
import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getUser():List<User>{
        delay(8000)
        val users:List<User> = listOf(
            User(1,"Sam"),
            User(2,"Taro"),
            User(3,"Jane"),
            User(4,"Amy")
        )


        return users
    }
}