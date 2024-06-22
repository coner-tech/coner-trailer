
import java.nio.file.Paths
import java.util.Properties
import kotlin.io.path.inputStream
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

val mavenProperties: Properties = file(Paths.get("target", "project.properties"))
    .inputStream()
    .buffered()
    .use {
        Properties()
            .apply { load(it) }
    }

version = mavenProperties["coner-trailer.version"]
    ?: throw IllegalStateException("coner-trailer.version property missing from mavenProperties")

repositories {
    maven("target/dependencies")
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation("tech.coner.trailer:toolkit-sample-dmvapp-common:$version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${mavenProperties["kotlinx-coroutines.version"]}")
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.4.0")
    implementation("com.materialkolor:material-kolor:1.7.0")
    implementation("org.kodein.di:kodein-di-framework-compose:${mavenProperties["kodein-di.version"]}")
    implementation("com.bumble.appyx:appyx-navigation-desktop:2.0.0")

    testImplementation(kotlin("test"))
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    testImplementation(compose.uiTest)
    testImplementation(compose.desktop.currentOs)
}

kotlin {
    compilerOptions {
        optIn.addAll(
            "androidx.compose.foundation.ExperimentalFoundationApi",
            "androidx.compose.material3.ExperimentalMaterial3Api",
            "androidx.compose.ui.test.ExperimentalTestApi"
        )
    }
}

compose.desktop {
    application {
        mainClass = "tech.coner.trailer.toolkit.sample.dmvapp.gui.DmvAppGuiKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "toolkit-sample-dmvapp-gui"
            packageVersion = "1.0.0"
        }
    }
}
