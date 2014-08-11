#!/bin/sh -e

# Start cmis on boot
if [ -x "/etc/init.d/cmis" ]; then
  if [ ! -e "/etc/init/cmis.conf" ]; then
    update-rc.d cmis defaults >/dev/null
  fi
fi

invoke-rc.d cmis start
