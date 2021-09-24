package com.raychal.githubusers.utils.state

class ResultState<out E>(val state: State, val data: E?, val message: String?) {
    companion object {
        fun <E> success(data: E): ResultState<E> = ResultState(State.SUCCESS, data, null)

        fun <E> error(data: E?, message: String): ResultState<E> = ResultState(State.ERROR, data, message)

        fun <E> loading(data: E?): ResultState<E> = ResultState(State.LOADING, data, null)
    }
}