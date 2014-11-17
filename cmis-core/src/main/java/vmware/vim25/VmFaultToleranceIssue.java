
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmFaultToleranceIssue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmFaultToleranceIssue">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VimFault">
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
@XmlType(name = "VmFaultToleranceIssue")
@XmlSeeAlso({
    SecondaryVmAlreadyEnabled.class,
    VmFaultToleranceOpIssuesList.class,
    CannotChangeDrsBehaviorForFtSecondary.class,
    InvalidOperationOnSecondaryVm.class,
    IncompatibleHostForFtSecondary.class,
    NotSupportedDeviceForFT.class,
    CannotChangeHaSettingsForFtSecondary.class,
    VmFaultToleranceConfigIssueWrapper.class,
    SecondaryVmAlreadyDisabled.class,
    FaultToleranceNotLicensed.class,
    VmFaultToleranceInvalidFileBacking.class,
    HostIncompatibleForFaultTolerance.class,
    NoHostSuitableForFtSecondary.class,
    VmFaultToleranceConfigIssue.class,
    SecondaryVmAlreadyRegistered.class,
    PowerOnFtSecondaryFailed.class,
    FaultTolerancePrimaryPowerOnNotAttempted.class,
    SecondaryVmNotRegistered.class,
    FtIssuesOnHost.class
})
public class VmFaultToleranceIssue
    extends VimFault
{


}
