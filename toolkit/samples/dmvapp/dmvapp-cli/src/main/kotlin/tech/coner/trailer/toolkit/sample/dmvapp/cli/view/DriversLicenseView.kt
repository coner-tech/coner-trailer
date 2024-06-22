package tech.coner.trailer.toolkit.sample.dmvapp.cli.view

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.VerticalAlign
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.table
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Strings

class DriversLicenseView(
    private val strings: Strings
) {
    operator fun invoke(driversLicense: DriversLicense)
    = table {
        header {
            row {
                cell(strings.dmvLabel) {
                    cellBorders = Borders.LEFT_TOP
                    columnSpan = 2
                }
                cell(strings.driversLicenseHeading) {
                    align = TextAlign.RIGHT
                    cellBorders = Borders.TOP_RIGHT
                }
            }
            row {
                cell(strings.dmvMotto) {
                    cellBorders = Borders.LEFT_RIGHT_BOTTOM
                    columnSpan = 3
                }
            }
        }
        body {
            row {
                cell(strings.driversLicensePhotoPlaceholder) {
                    rowSpan = 3
                    align = TextAlign.CENTER
                    verticalAlign = VerticalAlign.MIDDLE
                }
                cells(strings.driversLicenseNameField, driversLicense.name)
            }
            row {
                cells(strings.driversLicenseAgeField, driversLicense.age)
            }
            row {
                cells(strings.driversLicenseLicenseTypeField, strings.licenseTypesByObject[driversLicense.licenseType])
            }
        }
    }
}