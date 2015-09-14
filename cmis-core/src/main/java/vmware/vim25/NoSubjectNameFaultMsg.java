
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.324+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "NoSubjectNameFault", targetNamespace = "urn:vim25")
public class NoSubjectNameFaultMsg extends Exception {
    
    private vmware.vim25.NoSubjectName noSubjectNameFault;

    public NoSubjectNameFaultMsg() {
        super();
    }
    
    public NoSubjectNameFaultMsg(String message) {
        super(message);
    }
    
    public NoSubjectNameFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSubjectNameFaultMsg(String message, vmware.vim25.NoSubjectName noSubjectNameFault) {
        super(message);
        this.noSubjectNameFault = noSubjectNameFault;
    }

    public NoSubjectNameFaultMsg(String message, vmware.vim25.NoSubjectName noSubjectNameFault, Throwable cause) {
        super(message, cause);
        this.noSubjectNameFault = noSubjectNameFault;
    }

    public vmware.vim25.NoSubjectName getFaultInfo() {
        return this.noSubjectNameFault;
    }
}