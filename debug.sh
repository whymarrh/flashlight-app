#!/bin/bash

./uninstall.sh
ant debug
adb -d install ./bin/Flashlight-debug.apk
rm -rf bin
rm -rf gen
rm -rf libs
