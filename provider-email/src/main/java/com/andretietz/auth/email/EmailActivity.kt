package com.andretietz.auth.email

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.provider_email_activity_enter_email.*

class EmailActivity : AppCompatActivity() {

    companion object {

        const val RESULT_EMAIL = "result_email"
        const val RESULT_PASSWORD = "result_password"

        fun createIntent(context: Context): Intent {
            return Intent(context, EmailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.provider_email_activity_enter_email)

        buttonSignin.setOnClickListener {
            val data = Intent()
            data.putExtra(RESULT_EMAIL,
                    findViewById<TextView>(R.id.textEmail).text.toString())
            data.putExtra(RESULT_PASSWORD,
                    findViewById<TextView>(R.id.textPassword).text.toString())
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}