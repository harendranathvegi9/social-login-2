package com.rogalabs.lib

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rogalabs.lib.facebook.LoginFacebookPresenter
import com.rogalabs.lib.google.LoginGooglePresenter
import java.util.*

/**
 * Created by roga on 05/07/16.
 */
open class LoginView : AppCompatActivity(),  LoginContract.View {

    private var mainLayout: FrameLayout? = null
    private var googlePresenter: LoginContract.GooglePresenter? = null
    private var facebookPresenter: LoginContract.FacebookPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectPresenter(LoginGooglePresenter(this), LoginFacebookPresenter(this))
    }

    override fun onStart() {
        super.onStart()
        googlePresenter?.start(this)
        facebookPresenter?.start(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        googlePresenter?.destroy()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        mainLayout = layoutInflater.inflate(R.layout.login_view, null) as FrameLayout
        val activityContent = mainLayout!!.findViewById(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContent, true)
        super.setContentView(mainLayout)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        googlePresenter?.activityResult(requestCode, resultCode, data)
        facebookPresenter?.activityResult(requestCode, resultCode, data)
    }

    override fun injectPresenter(googlePresenter: LoginGooglePresenter, facebookPresenter: LoginFacebookPresenter) {
        this.googlePresenter   = googlePresenter
        this.facebookPresenter = facebookPresenter
    }


    override fun hideProgress() {
    }

    override fun showProgress() {
    }

    protected fun loginWithGoogle(callback: Callback) {
        googlePresenter?.signIn(callback)
    }

    protected fun logoffWithGoogle() {
        googlePresenter?.signOut()
    }

    protected fun loginWithFacebook(callback: Callback) {
        facebookPresenter?.signIn(callback)
    }

    protected fun logoffWithFacebook() {
        facebookPresenter?.signOut()
    }
}
