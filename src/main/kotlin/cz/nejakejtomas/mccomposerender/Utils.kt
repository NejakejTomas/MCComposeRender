package cz.nejakejtomas.mccomposerender

import kotlinx.atomicfu.atomic
import net.minecraft.resources.ResourceLocation

private val resourceLocationId = atomic(0L)
internal fun uniqueResourceLocation(): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(
        "cz.nejakejtomas.mccomposerender",
        "temp/${resourceLocationId.getAndIncrement()}"
    )
}