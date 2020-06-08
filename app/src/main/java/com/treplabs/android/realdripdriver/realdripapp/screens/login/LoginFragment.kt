package com.treplabs.android.realdripdriver.realdripapp.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.base.BaseViewModelFragment
import com.treplabs.android.realdripdriver.databinding.FragmentLoginBinding
import com.treplabs.android.realdripdriver.extensions.*
import com.treplabs.android.realdripdriver.networkutils.EventObserver
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        mainActivity.setUpToolBar("", true)
        binding.logInButton.setOnClickListener {
            if (!validateTextLayouts(
                    binding.emailEditText,
                    binding.passwordEditText
                )) return@setOnClickListener
            viewModel.logIn(
                binding.emailEditText.stringContent(),
                binding.passwordEditText.stringContent()
            )
        }

        viewModel.navigateDashBoard.observe(this, EventObserver {
            if (it) {
                Timber.d("Should navigate to dashboard now")
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTreatmentsFragment())
            }
        })
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
