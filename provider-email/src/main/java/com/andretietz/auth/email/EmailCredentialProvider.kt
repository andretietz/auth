package com.andretietz.auth.email

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.andretietz.auth.AndroidCredentialProvider
import com.andretietz.auth.AuthCredential
import com.andretietz.auth.credentials.EmailCredential
import io.reactivex.Maybe
import io.reactivex.subjects.MaybeSubject

class EmailCredentialProvider(private val activity: AppCompatActivity) : AndroidCredentialProvider {

    companion object {
        const val EMAIL_REQUEST_CODE = 0xE001
    }

    private var resultEmitter: MaybeSubject<AuthCredential> = MaybeSubject.create()

    override fun requestCredential(): Maybe<AuthCredential> {
        resultEmitter = MaybeSubject.create()
        return resultEmitter.doOnSubscribe {
            activity.startActivityForResult(EmailActivity.createIntent(activity), EMAIL_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != EMAIL_REQUEST_CODE) return
        when (resultCode) {
            Activity.RESULT_CANCELED -> resultEmitter.onComplete()
            Activity.RESULT_OK ->
                data?.let {
                    resultEmitter.onSuccess(
                            EmailCredential(
                                    data.getStringExtra(EmailActivity.RESULT_EMAIL),
                                    data.getStringExtra(EmailActivity.RESULT_PASSWORD)
                            )
                    )
                }
        }
    }

    override fun type(): String = EmailCredential.TYPE
}