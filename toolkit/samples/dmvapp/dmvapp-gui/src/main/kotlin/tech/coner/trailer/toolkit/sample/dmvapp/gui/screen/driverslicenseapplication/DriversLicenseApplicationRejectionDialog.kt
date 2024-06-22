package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.LocalStrings

@Composable
fun DriversLicenseApplicationRejectionDialog(
    onDismissRequest: () -> Unit,
    rejectionMessage: String
) {
    val strings = LocalStrings.current
    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    strings.driversLicenseApplicationRejectionTitle,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(
                    Modifier.size(8.dp)
                )
                Text(rejectionMessage)
                Spacer(
                    Modifier.size(8.dp)
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onDismissRequest
                ) {
                    Text(strings.ok)
                }
            }
        }
    }
}
