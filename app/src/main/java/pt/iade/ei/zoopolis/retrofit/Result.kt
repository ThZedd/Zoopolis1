package pt.iade.ei.zoopolis.retrofit

// Sealed class obriga a tratar todos os casos
sealed class Result<out T> {
    // 1. Corrigido para "Success" (dois 's')
    data class Success<out T>(val data: T?) : Result<T>()

    // 2. Adicionado 'exception' para resolver o erro do Log
    data class Error(val message: String, val exception: Throwable? = null) : Result<Nothing>()

    // 3. O estado que faltava no teu 'when'
    object Loading : Result<Nothing>()
}



