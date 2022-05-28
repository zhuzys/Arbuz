package kz.aues.arbuz

data class WaterMelon(
    val id: Int,
    var weight: Double,
    var status: WaterMelonState,
    var isChoice: Boolean = false
)