package com.example.testyuapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.ims.RcsUceAdapter
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener  {
    private var googleApiClient: GoogleApiClient? = null
    private var RC_SIGN_IN: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        btnLogin.setOnClickListener {
            var intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent,RC_SIGN_IN)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this,"Connect failed", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result);
        }
    }

    private fun handleSignInResult(ret:GoogleSignInResult?)
    {
        if(ret!!.isSuccess())
        {
            //Handle result
            var account = ret.signInAccount
        }else{
            Toast.makeText(this,"Sign in failed", Toast.LENGTH_LONG).show()
        }
    }
}