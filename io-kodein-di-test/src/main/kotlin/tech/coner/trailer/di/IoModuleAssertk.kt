package tech.coner.trailer.di

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.isSameInstanceAs
import assertk.assertions.prop

fun Assert<ConfigurationServiceArgument>.configDir() = prop(ConfigurationServiceArgument::configDir)
fun Assert<ConfigurationServiceArgument>.isDefaultInstance() = isSameInstanceAs(ConfigurationServiceArgument.Default)
fun Assert<ConfigurationServiceArgument>.isOverrideInstance() = isInstanceOf<ConfigurationServiceArgument.Override>()
