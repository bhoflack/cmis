
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VimFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VimFault">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}MethodFault">
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
@XmlType(name = "VimFault")
@XmlSeeAlso({
    GenericDrsFault.class,
    NotFound.class,
    AuthMinimumAdminPermission.class,
    CannotAccessLocalSource.class,
    InvalidName.class,
    HostIncompatibleForRecordReplay.class,
    InvalidPrivilege.class,
    ResourceNotAvailable.class,
    UserNotFound.class,
    AlreadyExists.class,
    OutOfBounds.class,
    CannotDisconnectHostWithFaultToleranceVm.class,
    AlreadyUpgraded.class,
    IORMNotSupportedHostOnDatastore.class,
    CannotMoveFaultToleranceVm.class,
    NoSubjectName.class,
    InvalidLocale.class,
    MismatchedBundle.class,
    LicenseEntityNotFound.class,
    InvalidIpmiLoginInfo.class,
    RecordReplayDisabled.class,
    MissingBmcSupport.class,
    PatchBinariesNotFound.class,
    RebootRequired.class,
    NoClientCertificate.class,
    ToolsUnavailable.class,
    SwapDatastoreUnset.class,
    NoDiskFound.class,
    InvalidBmcRole.class,
    InvalidIpmiMacAddress.class,
    ResourceInUse.class,
    ConcurrentAccess.class,
    InvalidFolder.class,
    Timedout.class,
    ExtendedFault.class,
    CannotMoveHostWithFaultToleranceVm.class,
    InvalidLicense.class,
    DasConfigFault.class,
    TooManyConsecutiveOverrides.class,
    DrsDisabledOnVm.class,
    HostPowerOpFailed.class,
    VAppConfigFault.class,
    DvsFault.class,
    InvalidAffinitySettingFault.class,
    PatchMetadataInvalid.class,
    VmMonitorIncompatibleForFaultTolerance.class,
    SSPIChallenge.class,
    FileFault.class,
    VmValidateMaxDevice.class,
    RemoveFailed.class,
    LimitExceeded.class,
    LicenseServerUnavailable.class,
    PatchNotApplicable.class,
    InvalidEvent.class,
    ProfileUpdateFailed.class,
    UnrecognizedHost.class,
    VmFaultToleranceIssue.class,
    InsufficientResourcesFault.class,
    CustomizationFault.class,
    LogBundlingFailed.class,
    HostConfigFault.class,
    HostConnectFault.class,
    InvalidLogin.class,
    VmToolsUpgradeFault.class,
    SnapshotFault.class,
    InvalidState.class,
    TaskInProgress.class,
    DuplicateName.class,
    UnsupportedVimApiVersion.class,
    MigrationFault.class,
    VmConfigFault.class,
    InvalidDatastore.class,
    OvfFault.class,
    NoCompatibleHost.class,
    ActiveDirectoryFault.class
})
public class VimFault
    extends MethodFault
{


}
