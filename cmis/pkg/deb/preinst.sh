#!/bin/sh -e
# Create cmis user and group
USERNAME="cmis"
GROUPNAME="cmis"
getent group "$GROUPNAME" >/dev/null || groupadd -r "$GROUPNAME"
getent passwd "$USERNAME" >/dev/null || \
  useradd -r -g "$GROUPNAME" -d /home/cmis -s /bin/false \
  -c "Capacity Management Information System" "$USERNAME"
ls /home/cmis || mkdir /home/cmis
chmod 700 /home/cmis
chown cmis /home/cmis
exit 0
