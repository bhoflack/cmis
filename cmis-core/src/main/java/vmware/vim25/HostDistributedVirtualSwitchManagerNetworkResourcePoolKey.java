
package vmware.vim25;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostDistributedVirtualSwitchManagerNetworkResourcePoolKey.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HostDistributedVirtualSwitchManagerNetworkResourcePoolKey">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="management"/>
 *     &lt;enumeration value="faultTolerance"/>
 *     &lt;enumeration value="vmotion"/>
 *     &lt;enumeration value="iSCSI"/>
 *     &lt;enumeration value="nfs"/>
 *     &lt;enumeration value="virtualMachine"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HostDistributedVirtualSwitchManagerNetworkResourcePoolKey")
@XmlEnum
public enum HostDistributedVirtualSwitchManagerNetworkResourcePoolKey {

    @XmlEnumValue("management")
    MANAGEMENT("management"),
    @XmlEnumValue("faultTolerance")
    FAULT_TOLERANCE("faultTolerance"),
    @XmlEnumValue("vmotion")
    VMOTION("vmotion"),
    @XmlEnumValue("iSCSI")
    I_SCSI("iSCSI"),
    @XmlEnumValue("nfs")
    NFS("nfs"),
    @XmlEnumValue("virtualMachine")
    VIRTUAL_MACHINE("virtualMachine");
    private final String value;

    HostDistributedVirtualSwitchManagerNetworkResourcePoolKey(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HostDistributedVirtualSwitchManagerNetworkResourcePoolKey fromValue(String v) {
        for (HostDistributedVirtualSwitchManagerNetworkResourcePoolKey c: HostDistributedVirtualSwitchManagerNetworkResourcePoolKey.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
