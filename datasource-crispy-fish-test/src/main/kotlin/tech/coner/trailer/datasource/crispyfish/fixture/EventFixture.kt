package tech.coner.trailer.datasource.crispyfish.fixture

import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.filetype.ecf.EventControlFileAssistant
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.trailer.SeasonEvent
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.extension
import kotlin.io.path.writeText

class EventFixture(
    private val seasonFixture: SeasonFixture,
    private val temp: Path,
    val coreSeasonEvent: SeasonEvent,
    val conePenalty: Int = 2
) {

    val ecfPath: Path
    val rggPath: Path
    val st1Path: Path

    init {
        require(coreSeasonEvent.event.crispyFish?.eventControlFile?.extension == "ecf")
        val ecfName = "/${coreSeasonEvent.event.crispyFish?.eventControlFile}"
        ecfPath = install(ecfName)
        rggPath = install(ecfName.replace(".ecf", ".rgg"))
        st1Path = install(ecfName.replace(".ecf", ".st1"))
    }

    fun eventControlFile(): EventControlFile {
        return EventControlFile(
            file = ecfPath.toFile(),
            classDefinitionFile = seasonFixture.classDefinitionFile,
            conePenalty = conePenalty,
            isTwoDayEvent = false,
            ecfAssistant = EventControlFileAssistant(),
            stagingFileAssistant = StagingFileAssistant()
        )
    }

    private fun install(name: String) = javaClass.getResourceAsStream(name).use {
        val text = it.bufferedReader().readText()
        val ecfPath = temp.resolve(name.substring(1))
        ecfPath.parent.createDirectories()
        ecfPath.createFile()
        ecfPath.writeText(text)
        ecfPath
    }
}