# cli-native

Native packaging module for the CLI app.

## Requirements

GraalVM, `native-image` installed

## Build

Enable building this module with profile `native`,

```shell
./mvnw -P native package
```

Because this module depends on GraalVM, it's not possible to build it with a standard JVM, therefore the module is not included by default.

## native-image config

`native-image` requires some configuration present in order to build the cli app. This configuration is checked into source, and generally won't change much (in theory). 

It was created using the `native-image-agent` attached to a regular shaded jar version of the cli app. Example:

```shell
java -agentlib:native-image-agent=config-merge-dir=cli-native/src/main/resources/META-INF/native-image -jar cli-native/target/cli-native-1.0-SNAPSHOT-shaded.jar event list
java -agentlib:native-image-agent=config-merge-dir=cli-native/src/main/resources/META-INF/native-image -jar cli-native/target/cli-native-1.0-SNAPSHOT-shaded.jar motorsportreg member list
```