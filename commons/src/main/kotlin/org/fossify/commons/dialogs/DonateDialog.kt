package org.fossify.commons.dialogs

import android.app.Activity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import org.fossify.commons.R
import org.fossify.commons.compose.alert_dialog.*
import org.fossify.commons.compose.components.LinkifyTextComponent
import org.fossify.commons.compose.extensions.MyDevices
import org.fossify.commons.compose.extensions.getActivity
import org.fossify.commons.compose.extensions.rememberMutableInteractionSource
import org.fossify.commons.compose.theme.AppThemeSurface
import org.fossify.commons.compose.theme.SimpleTheme
import org.fossify.commons.databinding.DialogDonateBinding
import org.fossify.commons.extensions.*

class DonateDialog(val activity: Activity) {
    init {
        val view = DialogDonateBinding.inflate(activity.layoutInflater, null, false).apply {
            dialogDonateImage.applyColorFilter(activity.getProperTextColor())
            dialogDonateText.text = Html.fromHtml(activity.getString(R.string.donate_short))
            dialogDonateText.movementMethod = LinkMovementMethod.getInstance()
            dialogDonateImage.setOnClickListener {
                // 删除：activity.launchViewIntent(R.string.thank_you_url)
            }
        }

        activity.getAlertDialogBuilder()
            .setNegativeButton(R.string.later, null)
            .apply {
                activity.setupDialogStuff(view.root, this, cancelOnTouchOutside = false)
            }
    }
}

@Composable
fun DonateAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier
) {
    val localContext = LocalContext.current.getActivity()
    // 删除：val donateIntent = {
    // 删除：localContext.launchViewIntent(R.string.thank_you_url)
    // 删除：donateIntent()
    androidx.compose.material3.AlertDialog(
        containerColor = dialogContainerColor,
        modifier = modifier
            .dialogBorder,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
        onDismissRequest = {},
        shape = dialogShape,
        tonalElevation = dialogElevation,
        dismissButton = {
            TextButton(onClick = {
                alertDialogState.hide()
            }) {
                Text(text = stringResource(id = R.string.later))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                // 删除：donateIntent()
                alertDialogState.hide()
            }) {
                Text(text = stringResource(id = R.string.purchase))
            }
        },
        title = {
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(SimpleTheme.dimens.icon.large)
                        .clickable(
                            indication = null,
                            interactionSource = rememberMutableInteractionSource(),
                            onClick = {
                                // 删除：donateIntent()
                                alertDialogState.hide()
                            }
                        ),
                    colorFilter = ColorFilter.tint(dialogTextColor)
                )
            }
        },
        text = {
            val source = stringResource(id = R.string.donate_short)
            LinkifyTextComponent(
                fontSize = 16.sp,
                removeUnderlines = false,
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER,
                modifier = Modifier.fillMaxWidth()
            ) {
                source.fromHtml()
            }
        }
    )
}

@Composable
@MyDevices
private fun DonateAlertDialogPreview() {
    AppThemeSurface {
        DonateAlertDialog(alertDialogState = rememberAlertDialogState())
    }
}


