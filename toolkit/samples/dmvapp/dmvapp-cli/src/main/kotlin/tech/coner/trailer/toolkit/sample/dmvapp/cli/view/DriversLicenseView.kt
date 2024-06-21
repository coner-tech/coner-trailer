package tech.coner.trailer.toolkit.sample.dmvapp.cli.view

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.VerticalAlign
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.table
import tech.coner.trailer.toolkit.sample.dmvapp.domain.entity.DriversLicense
import tech.coner.trailer.toolkit.sample.dmvapp.presentation.localization.Localization

class DriversLicenseView(
    private val localization: Localization
) {
    operator fun invoke(driversLicense: DriversLicense)
    = table {
        header {
            row {
                cell(localization.dmvLabel) {
                    cellBorders = Borders.LEFT_TOP
                    columnSpan = 2
                }
                cell(localization.driversLicenseHeading) {
                    align = TextAlign.RIGHT
                    cellBorders = Borders.TOP_RIGHT
                }
            }
            row {
                cell(localization.dmvMotto) {
                    cellBorders = Borders.LEFT_RIGHT_BOTTOM
                    columnSpan = 3
                }
            }
        }
        body {
            row {
                cell(localization.driversLicensePhotoPlaceholder) {
                    rowSpan = 3
                    align = TextAlign.CENTER
                    verticalAlign = VerticalAlign.MIDDLE
                }
                cells(localization.driversLicenseNameField, driversLicense.name)
            }
            row {
                cells(localization.driversLicenseAgeWhenAppliedField, driversLicense.ageWhenApplied)
            }
            row {
                cells(localization.driversLicenseLicenseTypeField, localization.licenseTypesByObject[driversLicense.licenseType])
            }
        }
    }
}