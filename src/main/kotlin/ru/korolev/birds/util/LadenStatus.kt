package ru.korolev.birds.util

class LadenStatus<B> private constructor(
    private var burden: B?,
    private val message: String?
){
    companion object {
        fun <B>ok(payload:B):LadenStatus<B> = LadenStatus(payload, null)
        fun <B>fail(message: String):LadenStatus<B> = LadenStatus(null, message)
    }

    fun getBurden():B = burden?: throw UnsupportedOperationException("Operation was failed and thus haven't any payload")
    fun getMessage():String = message?: throw UnsupportedOperationException("Operation was successful and thus haven't any message")

    fun isFailed():Boolean = burden == null
}