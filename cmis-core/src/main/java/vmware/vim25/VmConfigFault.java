
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmConfigFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmConfigFault">
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
@XmlType(name = "VmConfigFault")
@XmlSeeAlso({
    VmHostAffinityRuleViolation.class,
    CpuHotPlugNotSupported.class,
    VAppNotRunning.class,
    FaultToleranceCannotEditMem.class,
    UnsupportedVmxLocation.class,
    CannotDisableSnapshot.class,
    SoftRuleVioCorrectionDisallowed.class,
    MemoryHotPlugNotSupported.class,
    RuleViolation.class,
    EightHostLimitViolated.class,
    NumVirtualCpusIncompatible.class,
    GenericVmConfigFault.class,
    InvalidFormat.class,
    UnsupportedDatastore.class,
    VmConfigIncompatibleForRecordReplay.class,
    RDMNotSupportedOnDatastore.class,
    VmConfigIncompatibleForFaultTolerance.class,
    NoCompatibleHardAffinityHost.class,
    CannotAccessVmComponent.class,
    NoCompatibleSoftAffinityHost.class,
    VAppPropertyFault.class,
    VirtualHardwareCompatibilityIssue.class,
    InvalidVmConfig.class,
    SoftRuleVioCorrectionImpact.class
})
public class VmConfigFault
    extends VimFault
{


}
