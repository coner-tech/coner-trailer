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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberDI
import org.kodein.di.instance
import tech.coner.trailer.toolkit.sample.dmvapp.domain.service.impl.DriversLicenseApplicationServiceImpl
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.LocalStrings

@Composable
fun DriversLicenseApplicationServiceSettingsDialog(
    onDismissRequest: () -> Unit
) {
    val strings = LocalStrings.current
    val service: DriversLicenseApplicationServiceImpl by rememberDI { instance() }
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
                    strings.driversLicenseApplicationServiceSettings,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(Modifier.size(8.dp))

                var buildingOnFireChance by remember { mutableFloatStateOf(service.buildingOnFireChance) }
                FloatSetting(
                    label = strings.driversLicenseApplicationServiceBuildingOnFireChance,
                    value = buildingOnFireChance,
                    onValueChange = { buildingOnFireChance = it }
                )

                var sassChance by remember { mutableFloatStateOf(service.sassChance) }
                FloatSetting(
                    label = strings.driversLicenseApplicationServiceSassChance,
                    value = sassChance,
                    onValueChange = { sassChance = it },
                )

                var legallyProhibitedChance by remember { mutableFloatStateOf(service.legallyProhibitedChance) }
                FloatSetting(
                    label = strings.driversLicenseApplicationServiceLegallyProhibitedChance,
                    value = legallyProhibitedChance,
                    onValueChange = { legallyProhibitedChance = it },
                )

                Button(
                    onClick = {
                        service.buildingOnFireChance = buildingOnFireChance
                        service.sassChance = sassChance
                        service.legallyProhibitedChance = legallyProhibitedChance
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(strings.ok)
                }
            }
        }
    }
}

@Composable
private fun FloatSetting(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) {
    Text(label)
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange
    )
}
