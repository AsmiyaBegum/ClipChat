package com.ab.clipchat.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ab.clipchat.R
import com.ab.clipchat.databinding.FragmentProfileBinding
import com.ab.clipchat.signIn.DeviceRegistrationActivity
import com.ab.clipchat.signIn.UserData
import com.ab.clipchat.utils.Utils
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.clicks
import rx.android.schedulers.AndroidSchedulers

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewModel: ProfileViewModel

    private fun bind(){

       binding.logout.clicks()
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe {
               if(Utils.checkDeviceOnline(requireContext())){
                   viewModel.inputs.signOut {
                       startRegistrationActivity()
                   }
               }
           }

        viewModel.outputs.userData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                updateUserData(data)
            }
    }

    private fun startRegistrationActivity() {
        startActivity(Intent(requireActivity(), DeviceRegistrationActivity::class.java))
        requireActivity().finish()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        bind()
        viewModel.inputs.getUserData()

    }

    private fun updateUserData(userData: UserData){
        binding.email.text = userData.email.ifBlank { "-" }
        binding.userName.text = userData.userName.ifBlank { "-" }
        binding.phoneNumber.text = userData.phoneNumber.ifBlank { "-" }
        Glide.with(binding.root)
            .load(userData.profilePicture)
            .placeholder(R.drawable.ic_girl)
            .into(binding.profileImage)
//        binding.profileImage.setImageBitmap(userData.profilePicture)
        binding.lastLoggedIn.text = userData.loggedInTime.ifBlank { "-" }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}