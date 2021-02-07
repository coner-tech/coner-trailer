# Coner Trailer CLI

Coner Trailer CLI is a terminal app with all features of the Coner Trailer library.

## Run

### Development environment

In a development environment, you can build and run an executable jar as follows, from the repo root.

- Requires: JDK >= 11 (any vendor)
- Build: `./mvnw package`
- Run: `java -jar cli/target/cli-native-$version-shaded.jar`

### Native image

Optionally build a native image. This offers significant startup performance benefits compared to executing a JAR, but takes extra time to build. This will be the artifact distributed for end-users.

- Requires: GraalVM, Java 11 version
- Build: `./mvnw -P cli-native package`
- Run `./cli/target/coner-trailer-cli`

#### Configuration

`native-image` requires some configuration present in order to build the cli app. This configuration is checked into source, and generally won't change much (in theory).

In case it does need to change, build (see above) the `cli-native` profile, then use this recipe:

```shell
java -agentlib:native-image-agent=config-merge-dir=cli/src/native-image/ -jar cli/target/cli-$version-shaded.jar $args
```