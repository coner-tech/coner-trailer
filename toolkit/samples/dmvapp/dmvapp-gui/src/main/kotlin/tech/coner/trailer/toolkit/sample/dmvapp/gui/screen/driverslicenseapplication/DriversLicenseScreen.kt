package tech.coner.trailer.toolkit.sample.dmvapp.gui.screen.driverslicenseapplication

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import org.kodein.di.compose.withDI
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.LicenseType
import tech.coner.trailer.toolkit.sample.dmvapp.gui.di
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerBrandColors
import tech.coner.trailer.toolkit.sample.dmvapp.gui.theme.ConerTheme
import tech.coner.trailer.toolkit.sample.dmvapp.gui.util.strings

@Composable
fun DriversLicenseScreen(
    driversLicense: DriversLicense
) {
    DriversLicenseContent(
        driversLicense = driversLicense
    )
}

@Composable
private fun DriversLicenseContent(
    driversLicense: DriversLicense
) {
    Column {
        ConstraintLayout(
            modifier = Modifier
                .background(ConerBrandColors.LogoGray)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val (dmv, heading, dmvMotto) = createRefs()
            createHorizontalChain(dmv, heading, chainStyle = ChainStyle.SpreadInside)
            Text(
                text = strings.dmvLabel,
                color = ConerBrandColors.LogoWhite,
                modifier = Modifier
                    .constrainAs(dmv) {
                        start.linkTo(parent.start)
                        end.linkTo(heading.start, margin = 32.dp)
                    }
            )
            Text(
                text = strings.driversLicenseHeading,
                color = ConerBrandColors.LogoWhite,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .constrainAs(heading) {
                        start.linkTo(dmv.end, margin = 32.dp)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = strings.dmvMotto,
                color = ConerBrandColors.LogoWhite,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .constrainAs(dmvMotto) {
                        start.linkTo(parent.start)
                        top.linkTo(dmv.bottom)
                    }
            )
        }
        Spacer(Modifier.height(16.dp))
        ConstraintLayout {
            val (photoBox, nameField, name, ageField, age, typeField, type) = createRefs()
            val fieldStartBarrier = createEndBarrier(photoBox, margin = 16.dp)
            val fieldEndBarrier = createEndBarrier(nameField, ageField, typeField)
            val fieldTopBarrier = createTopBarrier(nameField, ageField, typeField)
            val fieldBottomBarrier = createBottomBarrier(nameField, ageField, typeField)
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier
                    .constrainAs(photoBox) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(fieldTopBarrier)
                        bottom.linkTo(fieldBottomBarrier)
                    }
            ) {
                Text(
                    text = strings.driversLicensePhotoPlaceholder
                )
            }
            Text(
                text = strings.driversLicenseNameField,
                modifier = Modifier
                    .constrainAs(nameField) {
                        start.linkTo(fieldStartBarrier)
                        top.linkTo(parent.top)
                        bottom.linkTo(name.bottom)
                    }
            )
            Text(
                text = driversLicense.name,
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(fieldEndBarrier, margin = 8.dp)
                        top.linkTo(nameField.top)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                text = strings.driversLicenseAgeField,
                modifier = Modifier
                    .constrainAs(ageField) {
                        start.linkTo(fieldStartBarrier)
                        top.linkTo(nameField.bottom)
                        bottom.linkTo(age.bottom)
                    }
            )
            Text(
                text = driversLicense.age.toString(),
                modifier = Modifier
                    .constrainAs(age) {
                        start.linkTo(name.start)
                        top.linkTo(ageField.top)
                    }
            )
            Text(
                text = strings.driversLicenseLicenseTypeField,
                modifier = Modifier
                    .constrainAs(typeField) {
                        start.linkTo(fieldStartBarrier)
                        top.linkTo(ageField.bottom)
                        bottom.linkTo(type.bottom)
                    }
            )
            Text(
                text = strings[driversLicense.licenseType],
                modifier = Modifier
                    .constrainAs(type) {
                        start.linkTo(name.start)
                        top.linkTo(typeField.top)
                    }
            )
        }
    }
}

@Composable
@Preview
private fun DriversLicensePreview() {
    withDI(di) {
        ConerTheme {
            Scaffold {
                DriversLicenseContent(
                    driversLicense = DriversLicense(
                        name = "John Doe",
                        age = 18,
                        licenseType = LicenseType.FullLicense
                    )
                )
            }
        }
    }
}
