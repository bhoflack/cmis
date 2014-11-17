
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HostEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HostEvent">
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
@XmlType(name = "HostEvent")
@XmlSeeAlso({
    HostCnxFailedNoConnectionEvent.class,
    VcAgentUninstalledEvent.class,
    HostDasDisablingEvent.class,
    NASDatastoreCreatedEvent.class,
    HostWwnChangedEvent.class,
    TimedOutHostOperationEvent.class,
    HostNonCompliantEvent.class,
    HostAddFailedEvent.class,
    HostAddedEvent.class,
    HostCnxFailedNotFoundEvent.class,
    CanceledHostOperationEvent.class,
    HostCompliantEvent.class,
    HostCnxFailedTimeoutEvent.class,
    AccountUpdatedEvent.class,
    AdminPasswordNotChangedEvent.class,
    DatastorePrincipalConfigured.class,
    HostUserWorldSwapNotEnabledEvent.class,
    HostCnxFailedBadVersionEvent.class,
    EnteredMaintenanceModeEvent.class,
    HostIpToShortNameFailedEvent.class,
    HostDasOkEvent.class,
    ExitedStandbyModeEvent.class,
    DrsResourceConfigureFailedEvent.class,
    IScsiBootFailureEvent.class,
    HostIpInconsistentEvent.class,
    HostCnxFailedBadUsernameEvent.class,
    HostEnableAdminFailedEvent.class,
    HostDasEnabledEvent.class,
    HostCnxFailedNoLicenseEvent.class,
    UserAssignedToGroup.class,
    HostSyncFailedEvent.class,
    VMFSDatastoreExtendedEvent.class,
    DatastoreRemovedOnHostEvent.class,
    UserPasswordChanged.class,
    HostUpgradeFailedEvent.class,
    HostRemovedEvent.class,
    HostGetShortNameFailedEvent.class,
    RemoteTSMEnabledEvent.class,
    ExitingStandbyModeEvent.class,
    HostConfigAppliedEvent.class,
    HostCnxFailedNetworkErrorEvent.class,
    HostWwnConflictEvent.class,
    VcAgentUninstallFailedEvent.class,
    HostReconnectionFailedEvent.class,
    LocalDatastoreCreatedEvent.class,
    HostCnxFailedCcagentUpgradeEvent.class,
    HostConnectedEvent.class,
    DuplicateIpDetectedEvent.class,
    HostComplianceCheckedEvent.class,
    HostAdminDisableEvent.class,
    HostIpChangedEvent.class,
    HostProfileAppliedEvent.class,
    DrsResourceConfigureSyncedEvent.class,
    HostCnxFailedAccountFailedEvent.class,
    LocalTSMEnabledEvent.class,
    HostDasErrorEvent.class,
    HostVnicConnectedToCustomizedDVPortEvent.class,
    EnteringMaintenanceModeEvent.class,
    HostCnxFailedNoAccessEvent.class,
    HostCnxFailedBadCcagentEvent.class,
    HostDasDisabledEvent.class,
    ExitStandbyModeFailedEvent.class,
    NoDatastoresConfiguredEvent.class,
    GhostDvsProxySwitchDetectedEvent.class,
    DatastoreRenamedOnHostEvent.class,
    EnteringStandbyModeEvent.class,
    UpdatedAgentBeingRestartedEvent.class,
    GhostDvsProxySwitchRemovedEvent.class,
    VMFSDatastoreCreatedEvent.class,
    HostDasEnablingEvent.class,
    VcAgentUpgradeFailedEvent.class,
    VimAccountPasswordChangedEvent.class,
    HostDisconnectedEvent.class,
    AccountCreatedEvent.class,
    UserUnassignedFromGroup.class,
    AccountRemovedEvent.class,
    HostAdminEnableEvent.class,
    HostCnxFailedAlreadyManagedEvent.class,
    EnteredStandbyModeEvent.class,
    ExitMaintenanceModeEvent.class,
    DatastoreDiscoveredEvent.class,
    HostShutdownEvent.class,
    VcAgentUpgradedEvent.class,
    HostConnectionLostEvent.class,
    HostDasEvent.class,
    HostShortNameToIpFailedEvent.class,
    HostCnxFailedEvent.class,
    VMFSDatastoreExpandedEvent.class
})
public class HostEvent
    extends Event
{


}
