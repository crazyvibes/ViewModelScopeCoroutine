# ViewModelScopeCoroutine
Mvvm architecture pattern using coroutine

## View model scope

-When we are working with android jetpack architecture components. When we are following mvvm architecture,
 We need to always create view models for activities and for fragments. 

-Now, during this lesson , and during next two lessons I am going to show you some fundamental code patterns
 for using coroutines with viewmodel and livedata. I am not going to create any large android applications
 during this lesson and during next two lessons.We will continue with small demo apps.

-But After that , during other lessons of this course we will start creating complete projects applying things
 we learnt during these Foundation level lessons. So, Here , for the demonstration I just created a new
 android studio project named ViewModelScope demo. And I also added required gradle dependencies to
 work with ViewModel and livedata as well as coroutines.

-Now I am creating a new viewModel class and naming it as MainActivityViewModel.

-Then, we need to extend the viewModel class. As we learnt during our previous lessons, in order to run
 a coroutine here in this view model we need a coroutine scope. First of all we need to define a coroutine scope. 

-private val myScope then, CoroutineScope, Let’s assume , we want to launch the corotine in a background thread.

-So we will use Dispathers.IO . .our coroutine will run on IO Dispatcher.

-Then I will create a function. Let’s say getUserData.

-And now , inside this function we can launch the coroutine in this way. But this is not complete yet.

-In android everytime a viewmodel is cleared from the memory, just before the clearing , viewmodel invokes its
 onCleared() method. onCleared() method is always there by default, but if we want to do something just before the 
 clearing we can manually override the onCleared method
 like this.

-Press control + o for override methods. Then from AndroidX.LifeCycle.ViewModel, select
 oncleared.

-Some of the coroutines we launch in a viewmodel has a potential to run even after the view model
 is cleared from the memory. It might will run until our app is terminated.

-In some scenarios that would be the intention.

-But If that's not what we intended app will be end up leaking memory . To avoid that we need to cancel
 the coroutine within this onCleared funciton. In order to cancel coroutines started in this scope
 We need to pass a job instance for the context of the coroutine scope.

-So I am going to create a job instance now. Private val myJob equals job now

-we will add this job to the context of the coroutine scope.

-This will allows us to control all the corotines launched in this scope.

-So,To cancel all the corotuiens launched in this scope, only thing we need to do is this.
 Canceling corouitnes manually

-like we just did, might be useful in some situations. But think, IF we have 20 viewmodel classes in our app,
 doing this manually might be unnecessary wasting of time.

-So, to avoid that, to avoid those boiler plate codes we can use viewModelScope.

-This new viewModelScope is bounded to ViewModel’s lifecycle. It has created to automatically handle cancellation
 when the ViewModel’s onClear() is called.

-We can easily use this scope from an extension function available on the viewmodel-ktx library.

-So, we need to add viewmodel-ktx dependency to the gradle.

-Android KTX is a set of Kotlin extensions that are included with Android Jetpack to provide even
 more concise, idiomatic Kotlin to Jetpack and Android platform APIs. To do so, KTX extensions leverage
 several Kotlin language features such as lambdas, extension functions and extension properties.

-Now , in this view model , we can simply use extension property viewModelScope instead of this
 my scope created by us. Let me show you how to do it. viewModelScope now we don’t need this scope or job.

-And we don’t need to override onCleared. Clearing will be done automatically.
 So, now code becomes very concise and even more easier to work with.

-So , this is how we do it. A ViewModelScope is defined for each ViewModel in our app. 
 Any coroutine launched in this scope will be automatically canceled if the ViewModel is cleared.

-This kind of coroutines are useful here, when you have work that needs to be done only if the ViewModel is active. Well,
 this lesson is almost over. But some of you might need some working project example with ViewModelScope.

-To see how ViewModelScope interact with other comeponets.

-For that I am going to very quickly implement a very basic project example.

-So if you don't need this code example you can just move to next lesson.

-It will save your time.

-I am going to get some user data from the repository to an activity through this view model. Instead of
 displaying them in a recycleview, to save time let’s just log the values.

-All right, let’s create a new data class called User.

-This user is going have an int id and a String name.

-Now , I am creating a new class for the Repository

-Here I am going to code a getter method to get a list of user instances. In a similar real world situation
 this can be a call to a rest api and get users form it or get a list of users from a local database.

-But here for the demonstration I am just creating a new list of objects and returning them.
 Let’s create a suspending function. suspend fun getUsers

-This will return a list of users.

-To mimic a long running task, I am delaying this task for 8 seconds.

-Now, we can create a list of users. val users: List equals list of

-Here I am going to create four user instances Then we will return the list of users.

-Now, it’s time to switch back to our view model class. I am creating a new repository instance here.
 private var, let's say, usersRepository equals UserRepository()
 inside this function.

-we have already lauched a coroutine using newly learned ViewModelScope property.

-I am creating a reference variable for the list of users . var
 result: List User

-Import the user And question mark to make the object nullable ?= null

-Now, I am going to get the list of user’s by invoking the getUsers function of UserRepository. It’s a
 long running task. So we should switch the thread of the coroutine using with context to a background thread.

-Dispatcher.IO, now , This coroutine will run on the IO dispatcher. So , it will run on the background

-We are going to send this list to the views as live data.

-For that Let’s create a new mutable live data instance of list of users.

-Then we can set this list of users as the value of mutablelivedata user instance.

-Let’s switch to main activity now. First of all we need to get the view model instance
 by using view model provider. private lateinit var mainActivityViewModel: MainActivityViewModel
 mainActivityViewModel equals View model provider, lifecycle owner is this MainActivity.

-So let's add this dot get MainActivityViewModel::class.java
 OK. Now we need to invoke getUserData() function of this mainActivityViewModel.

-Then , we will observe
 the users live data of mainActivityViewModel. mainActivityViewModel.users.observe
 Lifecycle owner is this main activity. Observer

-Let’s name the list of users we are getting form live data as myUsers. Then we can use a forEach block
 to iterate through those user objects.

-You can show these data on recyclerview or a textView But for now to save time I am going to just
 log the name of each user.

-Now, let’s run the app and see this in action.

-Good, app is working as expected. I just wanted to give you a working simple project example for
 ViewModelScope. So, That’s all for this lesson. We will definitely go to more practical code project examples
 during later sections of this course. During the next lesson of this section, we are going to study about
 lifecyclescope. Another poperty provided by our new KTX library. So thank you very much for lisntening .


##LiveDataBuilder

-Now, during this lesson we are going to disucss about newly introduced LiveData Builder.
 To use this we need to add livedata-ktx library 2.2.0-alpha01 or higher version to our project.

-To show you the benefits of this new feature, I am going to use the project we created during the first
 lesson of this section.

-Here we have added lifecycle and coroutine dependencies.
 We created a data class and named it as User.

-This user class has two properties. Int id and String name.

-Then we created a repository class named UserRepository.

-This UserRepository has a suspending function named getUsers, which returns a list of user instances.

-Here we have used this delay function to simulate a long running task. Then we created a ViewModel
 class, named it as MainActivityViewModel. here we have created this getUsers function. In that we have
 a viewModelScope to launch a new coroutine,

-we studied about viewModelScopes during the first lesson of this section, Then here
 we have defined a list . Then we have used withContext function to switch the thread of the coroutine
 to a background thread.

-And here, we have invoked the getUsers() function of the repository and get the list of User objects.
 And finally assigned that to the value of mutable live data.

-You can see, we need to invoke this getUsers function, before observing the values of this mutable livedata.

-In the main activity we wrote codes to invoke getUsers() function, and to Observe the list of users .
 If we run this app we will be able to see the log results. Let’s run the app ,log results will appear after 8 seconds
 delay.

-Yes, it worked as we expected.Alright. Now ,let me show you how to do this in a much easier
 and in a much efficient way .

-we need to add livedata ktx library to the gradle. Version should be 2.2.0-alpha01 or higher
 With the 2.2.0-alpha01 version, Android architecture components team introduced a new coroutine building
 block for livedata , This new block will automatically execute when the LiveData becomes active. 

-It automatically decides when to stop executing and cancel the coroutines inside the building block considering the state
 of the lifecycle owner. Inside the LiveData building block, you can use emit() function to set a value to the
 LiveData.

-Now, I am going back to MainActivityViewModel class. Let me show you how to do this using new livedata builder.
 I am commenting these first. var users equals liveData

-we need our long running task to be executed in a background thread. So I am adding Dispatchers.IO
 as the context.

-Now we need to get the user data from the repository.

-val result = usersRepository.getUsers()

-now we can emit the result .

-See, how many less code lines, we have now. Let’s move to MainActivity .

-We don’t need to invoke a function like this now. Let’s comment it.

-So let’s run the app very quickly again and see it in action

-So, here we go App is working as it was worked before, but this time with very less amout of code.