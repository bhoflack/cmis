
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.340+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "ConcurrentAccessFault", targetNamespace = "urn:vim25")
public class ConcurrentAccessFaultMsg extends Exception {
    
    private vmware.vim25.ConcurrentAccess concurrentAccessFault;

    public ConcurrentAccessFaultMsg() {
        super();
    }
    
    public ConcurrentAccessFaultMsg(String message) {
        super(message);
    }
    
    public ConcurrentAccessFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentAccessFaultMsg(String message, vmware.vim25.ConcurrentAccess concurrentAccessFault) {
        super(message);
        this.concurrentAccessFault = concurrentAccessFault;
    }

    public ConcurrentAccessFaultMsg(String message, vmware.vim25.ConcurrentAccess concurrentAccessFault, Throwable cause) {
        super(message, cause);
        this.concurrentAccessFault = concurrentAccessFault;
    }

    public vmware.vim25.ConcurrentAccess getFaultInfo() {
        return this.concurrentAccessFault;
    }
}