#!/bin/sh -e
# Create cmis user and group
USERNAME="cmis"
GROUPNAME="cmis"
getent group "$GROUPNAME" >/dev/null || groupadd -r "$GROUPNAME"
getent passwd "$USERNAME" >/dev/null || \
  useradd -r -g "$GROUPNAME" -d /usr/share/riemann -s /bin/false \
  -c "Capacity Management Information System" "$USERNAME"
exit 0
