package com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails

import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.RealtimeInfusion

object InfusionDataHelper {

    fun getStockInfusion(volumeToDispense: String) = RealtimeInfusion(
            "0",
            "start",
            "900", // ml/h
            true,
            "${60 * 60 * 1000}", //Milliseconds
             "0",
            "0",
            "1000",
            "100",
            "0",
             volumeToDispense,
            "100"
        )

    }