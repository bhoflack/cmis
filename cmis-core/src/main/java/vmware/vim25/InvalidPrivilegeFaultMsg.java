
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.876+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "InvalidPrivilegeFault", targetNamespace = "urn:vim25")
public class InvalidPrivilegeFaultMsg extends Exception {
    
    private vmware.vim25.InvalidPrivilege invalidPrivilegeFault;

    public InvalidPrivilegeFaultMsg() {
        super();
    }
    
    public InvalidPrivilegeFaultMsg(String message) {
        super(message);
    }
    
    public InvalidPrivilegeFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrivilegeFaultMsg(String message, vmware.vim25.InvalidPrivilege invalidPrivilegeFault) {
        super(message);
        this.invalidPrivilegeFault = invalidPrivilegeFault;
    }

    public InvalidPrivilegeFaultMsg(String message, vmware.vim25.InvalidPrivilege invalidPrivilegeFault, Throwable cause) {
        super(message, cause);
        this.invalidPrivilegeFault = invalidPrivilegeFault;
    }

    public vmware.vim25.InvalidPrivilege getFaultInfo() {
        return this.invalidPrivilegeFault;
    }
}