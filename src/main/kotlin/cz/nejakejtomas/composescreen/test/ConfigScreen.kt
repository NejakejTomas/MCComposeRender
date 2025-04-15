package cz.nejakejtomas.composescreen.test

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import cz.nejakejtomas.composescreen.ComposeScreen
import net.minecraft.network.chat.Component

class ConfigScreen : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent ->
            ComposeScreen(Component.literal("TMP"), parent) {
                Column(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .verticalScroll(rememberScrollState())
                ) {
                    BasicText("Hello World")
                    BasicText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean efficitur lacus rutrum tincidunt vehicula. Vestibulum in quam rhoncus, consequat quam eget, imperdiet lectus. Integer egestas nunc non mi ornare pulvinar. Mauris nec turpis ultricies, rhoncus neque a, pulvinar arcu. Donec arcu risus, accumsan sed mattis semper, interdum at dolor. Suspendisse semper nisl ante, in aliquam lorem blandit eget. Vivamus ligula elit, congue aliquam urna vel, tincidunt porttitor odio. Morbi quis est id eros bibendum hendrerit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec id tempus urna, vitae bibendum dolor. Aliquam erat volutpat. Nam non euismod urna. Duis at libero porta, posuere quam eu, sollicitudin turpis.")
                    BasicText("Nulla tempor lectus ut venenatis venenatis. Nullam ac ipsum massa. Donec sed mattis erat. Sed eu nisl sapien. Morbi consectetur elit vel est porttitor, consequat viverra lacus rhoncus. Maecenas massa eros, facilisis nec erat eget, vestibulum lacinia quam. Curabitur pharetra, leo eget pellentesque interdum, nisi purus fermentum ante, id tristique nunc tellus in lorem. Nunc aliquet, ex vel blandit condimentum, diam lectus posuere mauris, vel sollicitudin leo nisi sit amet elit. Aenean in auctor urna, quis hendrerit purus. Vestibulum porttitor scelerisque dictum. Etiam vehicula cursus ligula, eu pulvinar lacus.")
                    BasicText("Pellentesque enim tortor, fringilla non leo nec, ultrices scelerisque nibh. Donec tempor tincidunt purus, non vulputate tortor blandit quis. Donec vel posuere arcu. Fusce consectetur massa mi, nec egestas neque tempus at. Duis sollicitudin arcu nibh, auctor hendrerit odio lacinia ac. Maecenas luctus tristique est sit amet fringilla. Suspendisse libero purus, placerat sed risus varius, blandit pulvinar lorem. Ut sodales quis mauris nec vehicula. Nulla vitae arcu placerat, semper metus eget, placerat quam. Pellentesque lobortis molestie porta. Maecenas lacus ante, vulputate vel ligula quis, vulputate mattis justo. Proin ultrices, urna ut bibendum scelerisque, tellus enim semper est, sed rhoncus ante nisl non diam. Aliquam nec massa vestibulum, faucibus risus eget, auctor mauris. Donec aliquet nec risus ac pulvinar. Mauris eu urna tincidunt, eleifend sapien eget, finibus nunc. Nunc nibh nunc, sodales id augue ac, vestibulum pretium justo.")
                    BasicText("Vivamus viverra tortor ut lacus ultricies tristique. Duis blandit libero eget tortor ullamcorper, vitae dictum tortor ullamcorper. In enim ex, luctus in lorem in, varius posuere dui. Sed vel tempus sem. Mauris vel felis sed lectus malesuada aliquet. Nullam finibus turpis in metus bibendum ultricies. Duis sollicitudin hendrerit enim, commodo tincidunt ipsum auctor imperdiet. Nullam sit amet faucibus mauris, in semper lacus. Praesent at finibus dui, sed finibus mauris. Proin risus felis, rutrum at convallis ut, tristique suscipit massa. Duis nisl ipsum, porttitor sed rhoncus quis, pulvinar ut velit. In bibendum mi mauris, semper laoreet augue tincidunt vel. Curabitur pharetra posuere nisl eu interdum.")
                    BasicText("Etiam accumsan, elit id placerat volutpat, nisi ligula elementum urna, ut pharetra elit sapien non orci. Sed ipsum mauris, venenatis eget lobortis at, tincidunt vel libero. Etiam libero ex, venenatis eu elit nec, interdum mollis felis. Nam tortor massa, porttitor a eros sed, feugiat dapibus erat. Fusce tempus eget dui quis tincidunt. Aenean cursus massa semper erat dapibus euismod at at ipsum. Suspendisse potenti. Ut et massa eleifend ipsum rutrum volutpat.")

                    var text by remember { mutableStateOf("Hello") }
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                    )
                }
            }
        }
    }

}