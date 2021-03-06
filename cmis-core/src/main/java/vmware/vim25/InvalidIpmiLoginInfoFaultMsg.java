
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.835+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "InvalidIpmiLoginInfoFault", targetNamespace = "urn:vim25")
public class InvalidIpmiLoginInfoFaultMsg extends Exception {
    
    private vmware.vim25.InvalidIpmiLoginInfo invalidIpmiLoginInfoFault;

    public InvalidIpmiLoginInfoFaultMsg() {
        super();
    }
    
    public InvalidIpmiLoginInfoFaultMsg(String message) {
        super(message);
    }
    
    public InvalidIpmiLoginInfoFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIpmiLoginInfoFaultMsg(String message, vmware.vim25.InvalidIpmiLoginInfo invalidIpmiLoginInfoFault) {
        super(message);
        this.invalidIpmiLoginInfoFault = invalidIpmiLoginInfoFault;
    }

    public InvalidIpmiLoginInfoFaultMsg(String message, vmware.vim25.InvalidIpmiLoginInfo invalidIpmiLoginInfoFault, Throwable cause) {
        super(message, cause);
        this.invalidIpmiLoginInfoFault = invalidIpmiLoginInfoFault;
    }

    public vmware.vim25.InvalidIpmiLoginInfo getFaultInfo() {
        return this.invalidIpmiLoginInfoFault;
    }
}
