package com.example.newmvp

import android.content.Intent
import android.os.Bundle
import android.view.View

open class MVPDelegatePresenter<T : MVPDelegate>(private var view: T?) {

    fun attach(t: T) {
        this.view = t
    }

    fun detach() {
        this.view = null
    }

    open fun getView() = view as T
    fun isViewAttached() = view != null
    fun getDelegateView() = view?.getHostDelegate() ?: throw RuntimeException("delegate is null")

    open fun onCreateView(savedInstanceState: Bundle?, rootView: View?) {}
    open fun onDestroyView() {}
    open fun onCreate(bundle: Bundle?) {}
    open fun onStart() {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onStop() {}
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
    open fun onDestroy() {}
    open fun onSaveInstanceState(outState: Bundle?) {}
    open fun onRestoreInstanceState(savedInstanceState: Bundle?) {}

}