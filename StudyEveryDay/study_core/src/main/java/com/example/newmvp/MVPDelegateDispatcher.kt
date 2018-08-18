package com.example.newmvp

import android.content.Intent
import android.os.Bundle
import android.view.View

class MVPDelegateDispatcher {
    private val presenters: MutableList<MVPDelegatePresenter<*>> = mutableListOf()
    fun <V : MVPDelegate> addPresenter(presenter: MVPDelegatePresenter<V>) {
        if (presenter.isViewAttached() && !presenters.contains(presenter)) {
            presenters.add(presenter)
        }
    }

    private fun <V : MVPDelegate> removePresenter(presenter: MVPDelegatePresenter<V>) {
        if (presenters.contains(presenter)) {
            presenters.remove(presenter)
            if (presenter.isViewAttached()) {
                presenter.detach()
            }
        }
    }

    fun dispatchOnCreateView(savedInstanceState: Bundle?, rootView: View?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onCreateView(savedInstanceState, rootView)
            }
        }
    }

    fun dispatchOnCreate(bundle: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onCreate(bundle)
            }
        }
    }

    fun dispatchOnStart() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onStart()
            }
        }
    }

    fun dispatchOnResume() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onResume()
            }
        }
    }

    fun dispatchOnPause() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onPause()
            }
        }
    }

    fun dispatchOnStop() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onStop()
            }
        }
    }


    fun dispatchOnDestroy() {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onDestroy()
            }
            removePresenter(it)
        }
    }

    fun dispatchOnDestoryView(bundle: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onDestroyView()
            }
        }
    }

    fun dispatchOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun dispatchOnSaveInstanceState(outState: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onSaveInstanceState(outState)
            }
        }
    }

    fun dispatchOnRestoreInstanceState(savedInstanceState: Bundle?) {
        presenters.forEach {
            if (it.isViewAttached()) {
                it.onRestoreInstanceState(savedInstanceState)
            }
        }
    }
}