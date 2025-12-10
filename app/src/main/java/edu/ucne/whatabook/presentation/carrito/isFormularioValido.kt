package edu.ucne.whatabook.presentation.carrito

fun isFormularioValido(state: CartUiState): Boolean {
    val numeroLimpio = state.numeroTarjeta.replace(" ", "")
    return numeroLimpio.length == 16 &&
            state.nombreTitular.length >= 4 &&
            state.fechaExp.matches(Regex("""\d{2}/\d{2}""")) &&
            state.cvv.length == 3
}
