package com.example.newmvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseMVPActivity : SupportActivity(), MVPView {
    private val mvpDispatcher = MVPDispatcher()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPresenters()?.forEach {
            mvpDispatcher.addPresenter(it)
        }
        mvpDispatcher.dispatchOnCreate(intent?.extras)

    }

    open fun createPresenters(): Array<out MVPPresenter<*>>? = null

    override fun onStart() {
        super.onStart()
        mvpDispatcher.dispatchOnStart()
    }

    override fun onResume() {
        super.onResume()
        mvpDispatcher.dispatchOnResume()
    }

    override fun onPause() {
        super.onPause()
        mvpDispatcher.dispatchOnPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mvpDispatcher.dispatchOnActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDispatcher.dispatchOnSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        mvpDispatcher.dispatchOnRestoreInstanceState(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        mvpDispatcher.dispatchOnStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDispatcher.dispatchOnDestroy()
    }

    override fun getHostActivity(): FragmentActivity {
        return this
    }


}