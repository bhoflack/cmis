
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.817+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "RebootRequiredFault", targetNamespace = "urn:vim25")
public class RebootRequiredFaultMsg extends Exception {
    
    private vmware.vim25.RebootRequired rebootRequiredFault;

    public RebootRequiredFaultMsg() {
        super();
    }
    
    public RebootRequiredFaultMsg(String message) {
        super(message);
    }
    
    public RebootRequiredFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public RebootRequiredFaultMsg(String message, vmware.vim25.RebootRequired rebootRequiredFault) {
        super(message);
        this.rebootRequiredFault = rebootRequiredFault;
    }

    public RebootRequiredFaultMsg(String message, vmware.vim25.RebootRequired rebootRequiredFault, Throwable cause) {
        super(message, cause);
        this.rebootRequiredFault = rebootRequiredFault;
    }

    public vmware.vim25.RebootRequired getFaultInfo() {
        return this.rebootRequiredFault;
    }
}
