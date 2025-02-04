package apc.appcradle.radioplayer.constants

import android.os.Bundle
import androidx.media3.session.CommandButton
import androidx.media3.session.SessionCommand
import apc.appcradle.radioplayer.R

class Enums {
    companion object {
        private const val CUSTOM_COMMAND_REWIND_ACTION_ID = "REWIND_15"
        private const val CUSTOM_COMMAND_FORWARD_ACTION_ID = "FAST_FWD_15"

        enum class NotificationPlayerCustomCommandButton(
            val customAction: String,
            val commandButton: CommandButton,
        ) {
            REWIND(
                customAction = CUSTOM_COMMAND_REWIND_ACTION_ID,
                commandButton = CommandButton.Builder()
                    .setDisplayName("Rewind")
                    .setSessionCommand(SessionCommand(CUSTOM_COMMAND_REWIND_ACTION_ID, Bundle()))
                    .setIconResId(R.drawable.baseline_skip_previous_24)
                    .build(),
            ),
            FORWARD(
                customAction = CUSTOM_COMMAND_FORWARD_ACTION_ID,
                commandButton = CommandButton.Builder()
                    .setDisplayName("Forward")
                    .setSessionCommand(SessionCommand(CUSTOM_COMMAND_FORWARD_ACTION_ID, Bundle()))
                    .setIconResId(R.drawable.baseline_skip_next_24)
                    .build(),
            );
        }
    }
}