package vmware.vim25;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:08.270+02:00
 * Generated source version: 2.7.5
 * 
 */
@WebServiceClient(name = "VimService", 
                  wsdlLocation = "vimService.wsdl",
                  targetNamespace = "urn:vim25Service") 
public class VimService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:vim25Service", "VimService");
    public final static QName VimPort = new QName("urn:vim25Service", "VimPort");
    static {
        URL url = VimService.class.getResource("vimService.wsdl");
        if (url == null) {
            url = VimService.class.getClassLoader().getResource("vimService.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(VimService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "vimService.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public VimService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public VimService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VimService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VimService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VimService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public VimService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns VimPortType
     */
    @WebEndpoint(name = "VimPort")
    public VimPortType getVimPort() {
        return super.getPort(VimPort, VimPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns VimPortType
     */
    @WebEndpoint(name = "VimPort")
    public VimPortType getVimPort(WebServiceFeature... features) {
        return super.getPort(VimPort, VimPortType.class, features);
    }

}
