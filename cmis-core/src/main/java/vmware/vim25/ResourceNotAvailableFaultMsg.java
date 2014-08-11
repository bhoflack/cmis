
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.376+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "ResourceNotAvailableFault", targetNamespace = "urn:vim25")
public class ResourceNotAvailableFaultMsg extends Exception {
    
    private vmware.vim25.ResourceNotAvailable resourceNotAvailableFault;

    public ResourceNotAvailableFaultMsg() {
        super();
    }
    
    public ResourceNotAvailableFaultMsg(String message) {
        super(message);
    }
    
    public ResourceNotAvailableFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotAvailableFaultMsg(String message, vmware.vim25.ResourceNotAvailable resourceNotAvailableFault) {
        super(message);
        this.resourceNotAvailableFault = resourceNotAvailableFault;
    }

    public ResourceNotAvailableFaultMsg(String message, vmware.vim25.ResourceNotAvailable resourceNotAvailableFault, Throwable cause) {
        super(message, cause);
        this.resourceNotAvailableFault = resourceNotAvailableFault;
    }

    public vmware.vim25.ResourceNotAvailable getFaultInfo() {
        return this.resourceNotAvailableFault;
    }
}
