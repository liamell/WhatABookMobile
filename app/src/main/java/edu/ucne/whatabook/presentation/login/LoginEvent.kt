package edu.ucne.whatabook.presentation.login


sealed class LoginEvent {
    data class UserNameChanged(val value: String): LoginEvent()
    data class PasswordChanged(val value: String): LoginEvent()
    object SubmitLogin: LoginEvent()
    object ShowRegister: LoginEvent()
}