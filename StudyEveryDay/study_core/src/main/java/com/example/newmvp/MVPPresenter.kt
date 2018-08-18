package com.example.newmvp

import android.content.Intent
import android.os.Bundle

open class MVPPresenter<T : MVPView>(private var view: T?) {


    fun attach(t: T) {
        this.view = t
    }

    fun detach() {
        this.view = null
    }

    fun getView() = view as T
    fun isViewAttached() = view != null
    fun getActivity() = view?.getHostActivity()
            ?: throw RuntimeException("Could not call getActivity if the View is not attached")

    open fun onCreate(bundle: Bundle?) {}
    open fun onStart(){}
    open fun onRestart(){}
    open fun onResume(){}
    open fun onPause(){}
    open fun onStop(){}
    open fun onActivityResult(requestCode:Int,resultCode:Int,data :Intent?){}
    open fun onDestroy(){}
    open fun onSaveInstanceState(outState: Bundle?){}
    open fun onRestoreInstanceState(savedInstanceState: Bundle?){}


}