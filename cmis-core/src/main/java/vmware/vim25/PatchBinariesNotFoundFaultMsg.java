
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.808+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "PatchBinariesNotFoundFault", targetNamespace = "urn:vim25")
public class PatchBinariesNotFoundFaultMsg extends Exception {
    
    private vmware.vim25.PatchBinariesNotFound patchBinariesNotFoundFault;

    public PatchBinariesNotFoundFaultMsg() {
        super();
    }
    
    public PatchBinariesNotFoundFaultMsg(String message) {
        super(message);
    }
    
    public PatchBinariesNotFoundFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public PatchBinariesNotFoundFaultMsg(String message, vmware.vim25.PatchBinariesNotFound patchBinariesNotFoundFault) {
        super(message);
        this.patchBinariesNotFoundFault = patchBinariesNotFoundFault;
    }

    public PatchBinariesNotFoundFaultMsg(String message, vmware.vim25.PatchBinariesNotFound patchBinariesNotFoundFault, Throwable cause) {
        super(message, cause);
        this.patchBinariesNotFoundFault = patchBinariesNotFoundFault;
    }

    public vmware.vim25.PatchBinariesNotFound getFaultInfo() {
        return this.patchBinariesNotFoundFault;
    }
}