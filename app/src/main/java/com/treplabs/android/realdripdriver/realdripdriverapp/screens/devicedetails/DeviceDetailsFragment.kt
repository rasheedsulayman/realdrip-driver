package com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.base.BaseViewModelFragment
import com.treplabs.android.realdripdriver.databinding.FragmentDeviceDetailsBinding
import com.treplabs.android.realdripdriver.extensions.stringContent
import com.treplabs.android.realdripdriver.extensions.validateTextLayouts
import com.treplabs.android.realdripdriver.networkutils.EventObserver
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.RealtimeInfusion
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class DeviceDetailsFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDeviceDetailsBinding
    private lateinit var viewModel: DeviceDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerAppComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DeviceDetailsViewModel::class.java)
        binding.viewModel = viewModel
        mainActivity.setUpToolBar("", true)
        binding.continueButton.setOnClickListener {
            if (!validateTextLayouts(
                    binding.deviceIdEditText,
                    binding.volumeToDispenseEditText
                )
            ) return@setOnClickListener
            viewModel.setInfusion(
                binding.deviceIdEditText.stringContent(),
                binding.infusionIdEditText.stringContent(),
                binding.volumeToDispenseEditText.stringContent()
            )
        }

        viewModel.navigateInfusionDetails.observe(this, EventObserver {
            findNavController().navigate(
                DeviceDetailsFragmentDirections.actionDeviceDetailsFragmentToInfusionDetailsFragment(
                    it
                )
            )
        })
    }

    override fun getViewModel(): BaseViewModel = viewModel
}

@Parcelize
data class InfusionDetails(
    val deviceId: String,
    val infusionId: String,
    val realtimeInfusion: RealtimeInfusion
) : Parcelable