
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.842+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "NoDiskFoundFault", targetNamespace = "urn:vim25")
public class NoDiskFoundFaultMsg extends Exception {
    
    private vmware.vim25.NoDiskFound noDiskFoundFault;

    public NoDiskFoundFaultMsg() {
        super();
    }
    
    public NoDiskFoundFaultMsg(String message) {
        super(message);
    }
    
    public NoDiskFoundFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDiskFoundFaultMsg(String message, vmware.vim25.NoDiskFound noDiskFoundFault) {
        super(message);
        this.noDiskFoundFault = noDiskFoundFault;
    }

    public NoDiskFoundFaultMsg(String message, vmware.vim25.NoDiskFound noDiskFoundFault, Throwable cause) {
        super(message, cause);
        this.noDiskFoundFault = noDiskFoundFault;
    }

    public vmware.vim25.NoDiskFound getFaultInfo() {
        return this.noDiskFoundFault;
    }
}
