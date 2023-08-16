package tech.coner.trailer.datasource.crispyfish.fixture

import tech.coner.crispyfish.CrispyFish
import tech.coner.trailer.SeasonEvent
import java.nio.file.Path
import kotlin.io.path.*

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
        val ecfName = "/${coreSeasonEvent.event.crispyFish?.eventControlFile?.invariantSeparatorsPathString}"
        ecfPath = install(ecfName)
        rggPath = install(ecfName.replace(".ecf", ".rgg"))
        st1Path = install(ecfName.replace(".ecf", ".st1"))
    }

    fun crispyFishEvent() = CrispyFish.event(ecfPath)

    private fun install(name: String) = javaClass.getResourceAsStream(name).use {
        val text = it.bufferedReader().readText()
        val ecfPath = temp.resolve(name.substring(1))
        ecfPath.parent.createDirectories()
        ecfPath.createFile()
        ecfPath.writeText(text)
        ecfPath
    }
}