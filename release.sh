#!/bin/bash
#
# 1. Update version in gradle.properties

./gradlew clean check uploadArchives

# 2. Update version in gradle.properties

./deploy_website.sh
