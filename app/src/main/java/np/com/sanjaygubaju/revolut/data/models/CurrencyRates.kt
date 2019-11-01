package np.com.sanjaygubaju.revolut.data.models

import com.google.gson.annotations.SerializedName


data class CurrencyRates(
    @SerializedName("base")
    val base: String = "",

    @SerializedName("date")
    val date: String = "",

    @SerializedName("rates")
    val currencies: Map<String, Double> = HashMap()

)