package com.example.newmvp

import android.os.Bundle
import android.view.View
import com.example.study_core.delegate.BaseDelegate

abstract class BaseMVPDelegate : BaseDelegate() {


    private val mvpDispatcher = MVPDelegateDispatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPresenters()?.forEach {
            mvpDispatcher.addPresenter(it)
        }
        mvpDispatcher.dispatchOnCreate(savedInstanceState)
    }

    open fun createPresenters(): Array<out MVPDelegatePresenter<*>>? = null

    override fun onStart() {
        super.onStart()
        mvpDispatcher.dispatchOnStart()
    }

    override fun onResume() {
        super.onResume()
        mvpDispatcher.dispatchOnResume()
    }


    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mvpDispatcher.dispatchOnCreateView(savedInstanceState, rootView)
    }

}