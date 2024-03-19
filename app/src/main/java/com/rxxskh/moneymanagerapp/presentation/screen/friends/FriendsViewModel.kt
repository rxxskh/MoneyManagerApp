package com.rxxskh.moneymanagerapp.presentation.screen.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxxskh.utils.Resource
import com.rxxskh.domain.user.model.User
import com.rxxskh.domain.friend.usecase.AddFriendUseCase
import com.rxxskh.domain.friend.usecase.DeleteFriendUseCase
import com.rxxskh.domain.friend.usecase.GetFriendsUseCase
import com.rxxskh.moneymanagerapp.common.TestValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val addFriendUseCase: AddFriendUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase
) : ViewModel() {

    var authorizedUser by mutableStateOf<User?>(null)
        private set
    var friendNames by mutableStateOf<List<String>>(emptyList())
        private set

    var newFriendLogin by mutableStateOf<String>("")
        private set

    var addFriendButtonActive by mutableStateOf<Boolean>(true)
        private set
    var addFriendErrorMessage by mutableStateOf<String>("")
        private set

    init {
        loadData()
    }

    fun updateNewFriendLogin(input: String) {
        newFriendLogin = input.trim()
    }

    fun clearNewFriendLogin() {
        newFriendLogin = ""
    }

    fun addFriend() {
        if (isCorrectNewFriend()) {
            addFriendButtonActive = false
            addFriendUseCase(
                friendName = newFriendLogin
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        addFriendErrorMessage = ""
                        val newValue = friendNames.toMutableList()
                        newValue.add(newFriendLogin)
                        friendNames = newValue
                    }

                    is Resource.Error -> {
                        addFriendErrorMessage = result.message!!
                    }
                }
            }.launchIn(viewModelScope).invokeOnCompletion { addFriendButtonActive = true }
        } else {
            addFriendErrorMessage = "Некорректный ввод"
        }
    }

    fun deleteFriend(input: String) {
        val newValue = friendNames.toMutableList()
        newValue.remove(input)
        friendNames = newValue
        deleteFriendUseCase(
            friendName = input
        ).onEach {}.launchIn(viewModelScope)
    }

    private fun isCorrectNewFriend(): Boolean =
        authorizedUser!!.userLogin != newFriendLogin && friendNames.indexOf(newFriendLogin) == -1

    private fun loadData() {
        authorizedUser = TestValues.authUser
        loadFriend()
    }

    private fun loadFriend() {
        getFriendsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    friendNames = result.data!!.map { it.userLogin }
                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}