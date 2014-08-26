
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.797+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "VimFaultFault", targetNamespace = "urn:vim25")
public class VimFaultFaultMsg extends Exception {
    
    private vmware.vim25.VimFault vimFaultFault;

    public VimFaultFaultMsg() {
        super();
    }
    
    public VimFaultFaultMsg(String message) {
        super(message);
    }
    
    public VimFaultFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public VimFaultFaultMsg(String message, vmware.vim25.VimFault vimFaultFault) {
        super(message);
        this.vimFaultFault = vimFaultFault;
    }

    public VimFaultFaultMsg(String message, vmware.vim25.VimFault vimFaultFault, Throwable cause) {
        super(message, cause);
        this.vimFaultFault = vimFaultFault;
    }

    public vmware.vim25.VimFault getFaultInfo() {
        return this.vimFaultFault;
    }
}