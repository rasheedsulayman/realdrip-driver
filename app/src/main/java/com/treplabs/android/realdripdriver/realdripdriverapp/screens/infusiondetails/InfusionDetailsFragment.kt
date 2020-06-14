package com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.base.BaseViewModelFragment
import com.treplabs.android.realdripdriver.databinding.FragmentInfusionDetailsBinding
import com.treplabs.android.realdripdriver.networkutils.EventObserver
import javax.inject.Inject

class InfusionDetailsFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentInfusionDetailsBinding
    private lateinit var viewModel: InfusionDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfusionDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        daggerAppComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(InfusionDetailsViewModel::class.java)
        binding.viewModel = viewModel
        val infusionDetails = InfusionDetailsFragmentArgs.fromBundle(arguments!!).infusionDetails
        viewModel.restartSimulation(infusionDetails)
        viewModel.subscribeToRealTimeInfusion(infusionDetails)
        binding.restartButton.setOnClickListener {
            viewModel.resetInfusion(infusionDetails)
        }

        binding.sendNotificationButton.setOnClickListener {
            viewModel.sendNotification(infusionDetails.infusionId, infusionDetails.deviceId)
        }

        binding.stopButton.setOnClickListener {
            viewModel.stopSimulation()
        }
    }


    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("InfusionDump", false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
