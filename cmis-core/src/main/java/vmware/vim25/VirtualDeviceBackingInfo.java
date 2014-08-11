
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualDeviceBackingInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualDeviceBackingInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualDeviceBackingInfo")
@XmlSeeAlso({
    VirtualDevicePipeBackingInfo.class,
    VirtualEthernetCardDistributedVirtualPortBackingInfo.class,
    VirtualDeviceRemoteDeviceBackingInfo.class,
    VirtualDeviceURIBackingInfo.class,
    VirtualDeviceDeviceBackingInfo.class,
    VirtualDeviceFileBackingInfo.class
})
public class VirtualDeviceBackingInfo
    extends DynamicData
{


}
