package com.example.newmvp

import com.example.study_core.delegate.BaseDelegate

interface MVPDelegate {
    fun getHostDelegate(): BaseDelegate
    fun startLoading()
    fun onLoadSuccess()
    fun onLoadError(error:String)
}