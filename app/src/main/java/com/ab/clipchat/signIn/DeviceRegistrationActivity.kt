package com.ab.clipchat.signIn

import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ab.clipchat.MainActivity
import com.ab.clipchat.R
import com.ab.clipchat.databinding.FragmentLoginBinding
import com.ab.clipchat.utils.Constants
import com.ab.clipchat.utils.Utils
import com.jakewharton.rxbinding.view.clicks
import rx.android.schedulers.AndroidSchedulers


class DeviceRegistrationActivity :  AppCompatActivity(){

    private lateinit var binding: FragmentLoginBinding

    lateinit var viewModel: DeviceRegisterationViewModel


    private fun bind(){
        binding.login.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(Utils.checkDeviceOnline(this)){
                    viewModel.inputs.signIn() { intent ->
                        startActivityForResult(intent!!,Constants.SIGN_IN_REQ_CODE)
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(DeviceRegisterationViewModel::class.java)
        bind()
        if(viewModel.inputs.isUserLoggedIn()){
            startMainActivity()
        }
        Utils.checkDeviceOnline(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.SIGN_IN_REQ_CODE) {
                Toast.makeText(this@DeviceRegistrationActivity,getString(R.string.login_success),Toast.LENGTH_SHORT).show()
                 viewModel.inputs.getSignInResultFromIntent(data!!){ signInData ->
                     if(signInData.errorMessage == null){
                         startMainActivity()
                     }
                 }
        }
    }


    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}

