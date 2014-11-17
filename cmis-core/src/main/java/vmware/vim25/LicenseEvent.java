
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LicenseEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LicenseEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}Event">
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
@XmlType(name = "LicenseEvent")
@XmlSeeAlso({
    LicenseNonComplianceEvent.class,
    UnlicensedVirtualMachinesFoundEvent.class,
    ServerLicenseExpiredEvent.class,
    AllVirtualMachinesLicensedEvent.class,
    LicenseServerAvailableEvent.class,
    InvalidEditionEvent.class,
    LicenseRestrictedEvent.class,
    NoLicenseEvent.class,
    IncorrectHostInformationEvent.class,
    LicenseServerUnavailableEvent.class,
    VMotionLicenseExpiredEvent.class,
    UnlicensedVirtualMachinesEvent.class,
    HostLicenseExpiredEvent.class,
    HostInventoryFullEvent.class
})
public class LicenseEvent
    extends Event
{


}
