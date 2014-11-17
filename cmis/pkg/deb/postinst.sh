#!/bin/sh -e

# Start cmis on boot
if [ -x "/etc/init.d/cmis" ]; then
  if [ ! -e "/etc/init/cmis.conf" ]; then
    update-rc.d cmis defaults >/dev/null
  fi
fi

chown -R cmis /var/log/cmis 
chmod -R 755 /var/log/cmis

invoke-rc.d cmis start
