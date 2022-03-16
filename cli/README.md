# Coner Trailer CLI

Coner Trailer CLI is a terminal app with all features of the Coner Trailer library.

## Run

### Development environment

In a development environment, you can build and run an executable jar as follows, from the repo root.

- Requires: JDK >= 17 (any vendor)
- Build: `./mvnw package`
- Run: `java -jar cli/target/cli-native-$version-shaded.jar`

### Native image

The CLI can build as a native image using GraalVM. This offers significant performance benefit compared to executing a
JAR. This will be the artifact distributed for end-users.

- Requires: GraalVM, Java 17 version, `native-image` component installed
- Build: `./mvnw -P cli-native package`
- Run `./cli/target/coner-trailer-cli`

#### Configuration

GraalVM native images need some configuration hints to support classes accessed by reflection. This especially
impacts Jackson data binding, manifesting as deserialization errors when there are new data models added, names changed,
etc. 

The configuration to provide those hints is checked into source at `/cli/src/native-image/`.

Whenever a developer adds or edits a class that is accessed by reflection, it is necessary to update the 
configuration hints to account for the changes. GraalVM provides an agent which can make those configuration changes
automatically by monitoring reflection use in active code paths. You just need to pass the appropriate arguments to
trigger the code paths impacted by your changes.

Recipe:

```shell
java -agentlib:native-image-agent=config-merge-dir=cli/src/native-image/ -jar cli/target/cli-$version-shaded.jar $args
```