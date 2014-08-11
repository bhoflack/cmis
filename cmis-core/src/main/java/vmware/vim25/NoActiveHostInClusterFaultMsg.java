
package vmware.vim25;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.5
 * 2013-06-17T16:08:07.790+02:00
 * Generated source version: 2.7.5
 */

@WebFault(name = "NoActiveHostInClusterFault", targetNamespace = "urn:vim25")
public class NoActiveHostInClusterFaultMsg extends Exception {
    
    private vmware.vim25.NoActiveHostInCluster noActiveHostInClusterFault;

    public NoActiveHostInClusterFaultMsg() {
        super();
    }
    
    public NoActiveHostInClusterFaultMsg(String message) {
        super(message);
    }
    
    public NoActiveHostInClusterFaultMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public NoActiveHostInClusterFaultMsg(String message, vmware.vim25.NoActiveHostInCluster noActiveHostInClusterFault) {
        super(message);
        this.noActiveHostInClusterFault = noActiveHostInClusterFault;
    }

    public NoActiveHostInClusterFaultMsg(String message, vmware.vim25.NoActiveHostInCluster noActiveHostInClusterFault, Throwable cause) {
        super(message, cause);
        this.noActiveHostInClusterFault = noActiveHostInClusterFault;
    }

    public vmware.vim25.NoActiveHostInCluster getFaultInfo() {
        return this.noActiveHostInClusterFault;
    }
}
