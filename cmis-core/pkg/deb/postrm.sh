#!/bin/sh -e

if [ "$1" = "purge" ] ; then
  update-rc.d cmis remove >/dev/null
fi
