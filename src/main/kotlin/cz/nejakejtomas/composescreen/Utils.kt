package cz.nejakejtomas.composescreen

import kotlinx.atomicfu.atomic
import net.minecraft.resources.ResourceLocation

private val resourceLocationId = atomic(0L)
fun uniqueResourceLocation(): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(
        "cz.nejakejtomas.composescreen",
        "temp/${resourceLocationId.getAndIncrement()}"
    )
}