# CMIS
## CMIS dashboard
You can find the dashboard at http://cmis.colo.elex.be:3000.

### CI Report
#### Listing products
The CMIS dashboard shows an overview of all products ( using information from the cmdb ) for which it could find measurement data in nagios.  This list is displayed at the left.

#### Product detail
When you click on a product name,  it will show a product summary and the installed instances.  The installed instances are parsed from the cmdb page.

Under the product summary you get a list of all measurements that have been associated with the product.  This is done by linking the measurement data for the host to the hostnames in the installed instances.

Each graph shows a trend for the different hostnames that are running the product.

#### Adding instances to a product in cmis
Update the installed instances on http://cmdb.elex.be.

### API
CMIS provides a REST api to access the data.  The base url for the API is: http://cmis.colo.elex.be:3000/cis

#### Listing all products
You can list all products by performing a GET to /.  This will return a list of objects with the property name and as value the product name.

    Method: GET
    Endpoint: /
    Parameters:
    Returns: a list of objects with as property name,  and as value the product name

example:

    ➜  ~  curl cmis-test.colo.elex.be:3000/cis/
    [{"name":"CAS"},{"name":"CMDB"},{"name":"CMIS - Capacity Management Information System"},{"name":"Centralized logging"},{"name":"Cfengine"},{"name":"Confluence"},{"name":"Cordat-Archiving"},{"name":"Discoverer 11g"},{"name":"Drupal"},{"name":"Electronic Wafermapping - EWAF"},{"name":"Garchive"},{"name":"Gitlab"},{"name":"HP iMC"},{"name":"Jenkins"},{"name":"Mainrouter"},{"name":"MySQL"},{"name":"Nagios tools"},{"name":"Oracle database"},{"name":"Samba4"},{"name":"oracle8services"}]
    
#### Getting the info for a given product name
The detailed product info for a product can be found by performing a GET to /product with parameter name.

    Method: GET
    Endpoint: /product
    Parameters:
      - name: the string containing the product name
    Returns:
      An object containing:
      - name: the string containing the product name
      - description: the string containing the description
      - critical_level: the integer containing the critical\_level
      - instances: the list of instance names
      - measurements: the list of measurements
        ( a measurement has the following structure:
        - name: the string containing the measurement name
        - labels: the labels to show on the graphs
        - data: a map containing the instances, and as value a list of all values)
    
example:

    ➜  ~  curl "http://cmis-test.colo.elex.be:3000/cis/product?name=Electronic%20Wafermapping%20-%20EWAF"                                    
    {"name":"Electronic Wafermapping - EWAF","description":"As product flows are getting more complicated with a lot of internal and external processes,  we need a product to integrate the processes automatically.","critical_level":5,"instances":["ewaf-test.colo.elex.be"],"measurements":[{"name":"cpu idle","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[92.0000000000000000,95.1142857142857143,91.0000000000000000,82.4545454545454545,94.6875000000000000,98.3636363636363636,87.0294117647058824]}},{"name":"cpu io wait","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[0E-20,0.25714285714285714286,5.0000000000000000,3.5454545454545455,1.8750000000000000,0E-20,9.8529411764705882]}},{"name":"cpu system","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[0.66666666666666666667,0.65714285714285714286,0.40000000000000000000,0.68181818181818181818,0.37500000000000000000,0.18181818181818181818,0.23529411764705882353]}},{"name":"cpu user","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[4.6666666666666667,2.4000000000000000,1.8000000000000000,11.9090909090909091,1.5000000000000000,0.36363636363636363636,1.3235294117647059]}},{"name":"cpu utilisation","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[0E-20,0.19354838709677419355,0E-20,0E-20,1.3333333333333333,0E-20,0.12000000000000000000]}},{"name":"reads","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[0E-20,0E-20,0E-20,0E-20,0E-20,0E-20,0E-20]}},{"name":"writes","labels":["2013\/12","2014\/01","2014\/02","2014\/04","2014\/05","2014\/07","2014\/08"],"data":{"ewaf-test.colo.elex.be":[0E-20,67.7419354838709677,0E-20,35.6000000000000000,36.5333333333333333,34.8000000000000000,27.0400000000000000]}}]}
