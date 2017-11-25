package com.andretietz.auth.demo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import com.andretietz.auth.AuthClient
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.CredentialProvider
import com.andretietz.auth.CompositeAndroidCredentialProvider
import com.andretietz.auth.model.User
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var client: AuthClient<User>
    @Inject lateinit var provider: CompositeAndroidCredentialProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerProviders.adapter = ArrayAdapter<CredentialProvider>(this, android.R.layout.activity_list_item,
                android.R.id.text1, ArrayList(provider.providers.values))

        buttonSignIn.isEnabled = false
        client.isSignedIn()
                .doOnComplete { buttonState(false) }
                .subscribe { user ->
                    Snackbar.make(rootView, "Welcome ${user.name}", Snackbar.LENGTH_LONG).show()
                    buttonState(true)
                }

    }

    private fun signInWith(providerType: String) {
        provider.authenticate(providerType)
                .subscribe({ credential -> signIn(credential) },
                        { error -> Snackbar.make(rootView, error.message!!, Snackbar.LENGTH_SHORT).show() })
    }

    private fun signIn(credential: AuthCredential) {
        progressBar.visibility = View.VISIBLE
        buttonSignIn.isEnabled = false
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
        buttonSignIn.text = if (loggedIn) "Logout" else "Login"
        spinnerProviders.isEnabled = !loggedIn
        buttonSignIn.isEnabled = true
        if (loggedIn) {
            buttonSignIn.setOnClickListener { signOut() }
        } else {
            buttonSignIn.setOnClickListener {
                val selectedProvider = spinnerProviders.selectedItem as CredentialProvider
                signInWith(selectedProvider.type())
            }
        }
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
