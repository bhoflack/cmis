# CMIS

## Installation

### Database schema
On the postgres database ( > 9 ) create a database cmis for user cmis.  Use utf-8 encoding.  Create the dataschema using the cmis-dataschema.sql script.

### Nagios archives
On the nagios server make sure there is a cmis user.  
Create a public key authentication from the the server where cmis is running ( user root ) to the nagios server.
Make sure that the cmis user can read `/var/log/nagios3/archives` and write to /tmp.

Install the cmis package

```dpkg -i cmis*.deb```

### Import the cmdb certificate
As the cmdb is behind SSL,  we need to import the certificate to our keystore.

First verify the location of the cacerts file:

```
ls -la /usr/lib/jvm/<<jdk version>>/jre/lib/security/cacerts
```

Then verify if the certificate is part of the keystore:

```
keytool -keystore /etc/ssl/certs/java/cacerts -list | grep cmdb
```

If you get an entry you should be fine.  If not execute the following steps:

Extract the SSL certificate:

```
openssl s_client -showcerts -connect cmdb.elex.be:443 </dev/null 2>/dev/null|openssl x509 -outform PEM > cmdb.elex.be.pem
```

Now add it to the keystore:

```
keytool -keystore /etc/ssl/certs/java/cacerts -importcert -alias cmdb.elex.be -file cmdb.elex.be.pem
```

### The configuration file
Configure /etc/cmis/cmis.config

This file is in [edn format](https://raw.githubusercontent.com/edn-format/).  At the moment the following keys are supported:

- datasource - The datasource to the postgres database.
- username - The string containing the username on the nagios server.
- hostname - The string containing the hostname of the nagios server.
- private-key-path - The string containing the path to the private key file.
- cmdb-product-url - The string containing the url of the cmdb products page.  i.e. https://cmdb.elex.be/products

example:

```
{:datasource {:subprotocol "postgresql"
              :classname "org.postgresql.Driver"
              :subname "//postgresql1-test.colo.elex.be/cmis"
              :user "cmis"
              :password "cmis"}
 :username "cmis"
 :hostname "nagios-test.colo.elex.be"
 :private-key-path "/root/.ssh/id_rsa"
 :cmdb-product-url "https://cmdb.elex.be/products"
}
```

Restart cmis using the init script.  It should create a daily report.  It will also listen to port 3000 for the report.

## Start
invoke-rc.d cmis start

## Stop
invoke-rc.d cmis stop

## Backup
All data is stored in the database.  We assume that the db is correctly backed up.  The configuration is stored in cfengine.

## Restoring
Restore the db.  Run cfagent on the configured host.

## Monitoring
Verify if the http service is responding at http://cmis.colo.elex.be:3000/

## Configuration
All configuration is in /etc/cmis/cmis.config

## Decomission
