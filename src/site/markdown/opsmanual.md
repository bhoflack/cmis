# CMIS

## Installation

On the postgres database ( > 9 ) create a database cmis for user cmis.  Use utf-8 encoding.  Create the dataschema using the cmis-dataschema.sql script.

On the nagios server make sure there is a cmis user.  
Create a public key authentication from the the server where cmis is running ( user root ) to the nagios server.
Make sure that the cmis user can read `/var/log/nagios3/archives` and write to /tmp.

Install the cmis package

```dpkg -i cmis*.deb```

Configure /etc/cmis/cmis.config

This file is in [edn format](https://raw.githubusercontent.com/edn-format/).  At the moment the following keys are supported:

- datasource - The datasource to the postgres database.
- username - The string containing the username on the nagios server.
- hostname - The string containing the hostname of the nagios server.
- private-key-path - The string containing the path to the private key file.

example:

```{:datasource {:subprotocol "postgresql"
                 :classname "org.postgresql.Driver"
                 :subname "//postgresql1-test.colo.elex.be/cmis"
                 :user "cmis"
                 :password "cmis"}
    :username "cmis"
    :hostname "nagios-test.colo.elex.be"
    :private-key-path "/root/.ssh/id_rsa"
   }```

Restart cmis using the init script.  It should create a daily report.  It will also listen to port 3000 for the report.
