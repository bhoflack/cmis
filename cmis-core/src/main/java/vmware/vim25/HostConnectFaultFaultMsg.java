
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.489+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "HostConnectFaultFault", targetNamespace = "urn:vim25")
public class HostConnectFaultFaultMsg extends Exception {
    
    private vmware.vim25.HostConnectFault hostConnectFaultFault;

    public HostConnectFaultFaultMsg() {
        super();
    }
    
    public HostConnectFaultFaultMsg(String message) {
        super(message);
    }
    
    public HostConnectFaultFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public HostConnectFaultFaultMsg(String message, vmware.vim25.HostConnectFault hostConnectFaultFault) {
        super(message);
        this.hostConnectFaultFault = hostConnectFaultFault;
    }

    public HostConnectFaultFaultMsg(String message, vmware.vim25.HostConnectFault hostConnectFaultFault, Throwable cause) {
        super(message, cause);
        this.hostConnectFaultFault = hostConnectFaultFault;
    }

    public vmware.vim25.HostConnectFault getFaultInfo() {
        return this.hostConnectFaultFault;
    }
}