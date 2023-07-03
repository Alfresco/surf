#!/usr/bin/env bash

set -e

HTTP_CREDENTIALS=$1
DEPENDENCIES_DIR="$(dirname "${BASH_SOURCE[0]}")"

mvn -f $DEPENDENCIES_DIR -B clean install

function clone_and_install {
  local project_path=$DEPENDENCIES_DIR/projects/$1
  local branch_name=${2:-jakarta-migration}
  if [ ! -d "$project_path" ]; then
    if [ -z "$HTTP_CREDENTIALS" ]; then
      git clone --single-branch --branch $branch_name git@github.com:Alfresco/$1.git $project_path
    else
      git clone --single-branch --branch $branch_name https://${HTTP_CREDENTIALS}@github.com/Alfresco/$1.git $project_path
    fi
  fi
  mvn -f $project_path -B clean install -DskipTests -Dmaven.javadoc.skip=true
}

clone_and_install surf-webscripts

