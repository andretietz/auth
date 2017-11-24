package com.andretietz.auth.demo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.andretietz.auth.AuthClient
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.CompositeAndroidAuthProvider
import com.andretietz.auth.credentials.EmailCredential
import com.andretietz.auth.credentials.FacebookCredential
import com.andretietz.auth.credentials.GoogleCredential
import com.andretietz.auth.credentials.TwitterCredential
import com.andretietz.auth.model.User
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var client: AuthClient<User>
    @Inject lateinit var provider: CompositeAndroidAuthProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLoginEmail.setOnClickListener {
            signIn(EmailCredential(textEmail.text.toString(), textPassword.text.toString()))
        }
        buttonLoginGoogle.setOnClickListener { signInWith(GoogleCredential.TYPE) }
        buttonLoginFacebook.setOnClickListener { signInWith(FacebookCredential.TYPE) }
        buttonLoginTwitter.setOnClickListener { signInWith(TwitterCredential.TYPE) }

        buttonLogout.setOnClickListener { signOut() }
    }

    private fun signInWith(providerType: String) {
        provider.authenticate(providerType)
                .subscribe({ credential -> signIn(credential) },
                        { error -> Snackbar.make(rootView, error.message!!, Snackbar.LENGTH_SHORT).show() })
    }

    private fun signIn(credential: AuthCredential) {
        progressBar.visibility = View.VISIBLE
        client.signIn(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ -> progressBar.visibility = View.INVISIBLE }
                .subscribe({ user ->
                    buttonState(true)
                    Snackbar.make(rootView, "Welcome ${user.name} (${credential.type()})", Snackbar.LENGTH_LONG).show()
                }, { error ->
                    error.message?.let {
                        Snackbar.make(rootView, it, Snackbar.LENGTH_LONG).show()
                    }
                    Timber.e(error)
                })
    }

    private fun buttonState(loggedIn: Boolean) {
        buttonLogout.isEnabled = loggedIn
        buttonLoginEmail.isEnabled = !loggedIn
        buttonLoginGoogle.isEnabled = !loggedIn
        buttonLoginFacebook.isEnabled = !loggedIn
        buttonLoginTwitter.isEnabled = !loggedIn
    }

    private fun signOut() {
        client.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    buttonState(false)
                    Snackbar.make(rootView, "Bye bye ${user.name}", Snackbar.LENGTH_LONG).show()
                }, { error -> Timber.e(error) })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        provider.onActivityResult(requestCode, resultCode, data)
    }
}
