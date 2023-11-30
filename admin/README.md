# Coner Trailer Admin

Coner Trailer Admin is a CLI app focused on administration of Coner Trailer. It allows you to interact with all major
functions of the underlying Coner Trailer library, for example to set up events, manage all related records, and
generate event results.

## Run

### Development environment

In a development environment, you can build and run an executable jar as follows, from the repo root.

- Requires: JDK >= 17 (any vendor)
- Build: `./mvnw package`
- Run: `java -jar admin/target/coner-trailer-admin-shaded-*.jar`
