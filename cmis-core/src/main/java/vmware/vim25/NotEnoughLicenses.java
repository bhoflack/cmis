
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotEnoughLicenses complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotEnoughLicenses">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}RuntimeFault">
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
@XmlType(name = "NotEnoughLicenses")
@XmlSeeAlso({
    HostInventoryFull.class,
    InvalidEditionLicense.class,
    LicenseKeyEntityMismatch.class,
    LicenseExpired.class,
    LicenseSourceUnavailable.class,
    InventoryHasStandardAloneHosts.class,
    LicenseDowngradeDisallowed.class,
    VmLimitLicense.class,
    InUseFeatureManipulationDisallowed.class,
    NoLicenseServerConfigured.class,
    ExpiredFeatureLicense.class,
    LicenseRestricted.class,
    IncorrectHostInformation.class
})
public class NotEnoughLicenses
    extends RuntimeFault
{


}
