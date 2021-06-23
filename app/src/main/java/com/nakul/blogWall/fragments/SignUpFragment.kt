package com.nakul.blogWall.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.nakul.blogWall.MyApplication
import com.nakul.blogWall.R
import com.nakul.blogWall.databinding.FragmentSignUpBinding
import com.nakul.blogWall.models.network_event.Event
import com.nakul.blogWall.utils.UtilFunctions.load
import com.nakul.blogWall.viewmodels.LoginViewModel

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        binding.loginSwitch.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        setObserver()
        setClicks()

        return binding.root
    }

    private fun setClicks() {
        binding.loginSwitch.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame, SignInFragment()).commit()
        }

        binding.lButton.setOnClickListener {

            viewModel.signUpUser(
                binding.email.text.toString(),
                binding.name.text.toString(),
                binding.password.text.toString()
            )

        }
    }

    private fun setObserver() {
        viewModel.signupEvent.observe(viewLifecycleOwner, {
            if (it == null)
                return@observe
            when (it) {
                is Event.Loading -> {
                    binding.lButton.load()
                }
                is Event.Success -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
                    binding.lButton.text = getString(R.string.sign_up)
                }
                is Event.Error -> {
                    Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
                    binding.lButton.text = getString(R.string.sign_up)
                }
            }
        })
    }

}