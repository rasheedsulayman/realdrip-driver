package com.treplabs.android.realdripdriver.realdripapp.screens.createtreatment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.base.BaseViewModelFragment
import com.treplabs.android.realdripdriver.databinding.FragmentManageTreatmentBinding
import com.treplabs.android.realdripdriver.extensions.validateTextLayouts
import com.treplabs.android.realdripdriver.realdripapp.data.models.request.CreateInfusionRequest
import com.treplabs.android.realdripdriver.realdripapp.data.models.response.Infusion
import com.treplabs.android.realdripdriver.networkutils.EventObserver
import javax.inject.Inject

class ManageTreatmentFragment : BaseViewModelFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentManageTreatmentBinding
    private lateinit var viewModel: ManageTreatmentViewModel
    private var infusion: Infusion? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageTreatmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerAppComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ManageTreatmentViewModel::class.java)
        binding.viewModel = viewModel
        infusion = ManageTreatmentFragmentArgs.fromBundle(arguments!!).infusion?.also {
            binding.infusion = it
            binding.volumeToDispenseEditText.isEnabled = false
        }
        setUpToolbar()
        viewModel.navigateToTreatment.observe(this,
            EventObserver {
                findNavController().navigate(
                    ManageTreatmentFragmentDirections.actionManageTreatmentFragmentToTreatmentsFragment()
                )
            })

        binding.saveButton.setOnClickListener {
            if (!validateTextLayouts(
                    binding.patientNameEditText,
                    binding.diagnosesEditText,
                    binding.deviceIdEditText,
                    binding.liquidContentEditText,
                    binding.volumeToDispenseEditText,
                    binding.doctorsInstructionEditText
                )
            ) return@setOnClickListener
            val request = CreateInfusionRequest(
                binding.deviceIdEditText.stringContent(),
                binding.doctorsInstructionEditText.stringContent(),
                binding.patientNameEditText.stringContent(),
                binding.volumeToDispenseEditText.stringContent().toInt(),
                binding.diagnosesEditText.stringContent(),
                binding.liquidContentEditText.stringContent()
            )
            if (infusion != null) viewModel.updateInfusion(infusion!!, request)
            else viewModel.createInfusion(request)
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        val title = if (infusion != null) "Update Infusion" else "Create Infusion"
        setUpToolBar(title, false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
