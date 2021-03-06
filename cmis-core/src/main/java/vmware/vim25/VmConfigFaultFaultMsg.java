
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.258+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "VmConfigFaultFault", targetNamespace = "urn:vim25")
public class VmConfigFaultFaultMsg extends Exception {
    
    private vmware.vim25.VmConfigFault vmConfigFaultFault;

    public VmConfigFaultFaultMsg() {
        super();
    }
    
    public VmConfigFaultFaultMsg(String message) {
        super(message);
    }
    
    public VmConfigFaultFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public VmConfigFaultFaultMsg(String message, vmware.vim25.VmConfigFault vmConfigFaultFault) {
        super(message);
        this.vmConfigFaultFault = vmConfigFaultFault;
    }

    public VmConfigFaultFaultMsg(String message, vmware.vim25.VmConfigFault vmConfigFaultFault, Throwable cause) {
        super(message, cause);
        this.vmConfigFaultFault = vmConfigFaultFault;
    }

    public vmware.vim25.VmConfigFault getFaultInfo() {
        return this.vmConfigFaultFault;
    }
}
