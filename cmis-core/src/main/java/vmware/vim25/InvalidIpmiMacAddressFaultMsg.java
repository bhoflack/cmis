
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.839+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "InvalidIpmiMacAddressFault", targetNamespace = "urn:vim25")
public class InvalidIpmiMacAddressFaultMsg extends Exception {
    
    private vmware.vim25.InvalidIpmiMacAddress invalidIpmiMacAddressFault;

    public InvalidIpmiMacAddressFaultMsg() {
        super();
    }
    
    public InvalidIpmiMacAddressFaultMsg(String message) {
        super(message);
    }
    
    public InvalidIpmiMacAddressFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIpmiMacAddressFaultMsg(String message, vmware.vim25.InvalidIpmiMacAddress invalidIpmiMacAddressFault) {
        super(message);
        this.invalidIpmiMacAddressFault = invalidIpmiMacAddressFault;
    }

    public InvalidIpmiMacAddressFaultMsg(String message, vmware.vim25.InvalidIpmiMacAddress invalidIpmiMacAddressFault, Throwable cause) {
        super(message, cause);
        this.invalidIpmiMacAddressFault = invalidIpmiMacAddressFault;
    }

    public vmware.vim25.InvalidIpmiMacAddress getFaultInfo() {
        return this.invalidIpmiMacAddressFault;
    }
}
