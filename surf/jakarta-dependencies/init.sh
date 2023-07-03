#!/usr/bin/env bash

echo "=========================== Starting Init Script ==========================="
PS4="\[\e[35m\]+ \[\e[m\]"
set +e -v -x
pushd "$(dirname "${BASH_SOURCE[0]}")/../"

$(dirname "${BASH_SOURCE[0]}")/../../build-dependencies.sh $GIT_HTTP_CREDENTIALS

popd
set +vex
echo "=========================== Finishing Init Script =========================="

exit ${SUCCESS}