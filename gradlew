#!/usr/bin/env sh
#
# Simplified Gradle wrapper script.  This script delegates to a
# locally installed `gradle` binary.  It is provided to satisfy CI
# configuration that expects an executable named `gradlew`.  If
# Gradle is not installed on the system, the script will exit with
# an error and instruct the user to install Gradle or generate a
# full wrapper using `gradle wrapper`.

if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
else
  echo "Gradle is not installed. Please install Gradle or generate the wrapper with 'gradle wrapper'." >&2
  exit 1
fi