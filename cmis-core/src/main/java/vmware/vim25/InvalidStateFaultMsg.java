
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.237+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "InvalidStateFault", targetNamespace = "urn:vim25")
public class InvalidStateFaultMsg extends Exception {
    
    private vmware.vim25.InvalidState invalidStateFault;

    public InvalidStateFaultMsg() {
        super();
    }
    
    public InvalidStateFaultMsg(String message) {
        super(message);
    }
    
    public InvalidStateFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStateFaultMsg(String message, vmware.vim25.InvalidState invalidStateFault) {
        super(message);
        this.invalidStateFault = invalidStateFault;
    }

    public InvalidStateFaultMsg(String message, vmware.vim25.InvalidState invalidStateFault, Throwable cause) {
        super(message, cause);
        this.invalidStateFault = invalidStateFault;
    }

    public vmware.vim25.InvalidState getFaultInfo() {
        return this.invalidStateFault;
    }
}
