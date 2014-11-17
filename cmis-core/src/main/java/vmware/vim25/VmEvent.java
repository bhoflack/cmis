
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}Event">
 *       &lt;sequence>
 *         &lt;element name="template" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmEvent", propOrder = {
    "template"
})
@XmlSeeAlso({
    DrsRuleViolationEvent.class,
    VmSecondaryDisabledEvent.class,
    VmRelayoutUpToDateEvent.class,
    VmFailedRelayoutOnVmfs2DatastoreEvent.class,
    VmMacChangedEvent.class,
    VmUpgradingEvent.class,
    VmResourcePoolMovedEvent.class,
    VmSuspendedEvent.class,
    VmUuidConflictEvent.class,
    VmRenamedEvent.class,
    VmInstanceUuidConflictEvent.class,
    VmStaticMacConflictEvent.class,
    VmRelayoutSuccessfulEvent.class,
    VmUuidAssignedEvent.class,
    VmMaxRestartCountReached.class,
    VmDeployedEvent.class,
    VmResourceReallocatedEvent.class,
    VmMacConflictEvent.class,
    VmAutoRenameEvent.class,
    VmFaultToleranceTurnedOffEvent.class,
    VmStartRecordingEvent.class,
    VmDeployFailedEvent.class,
    VmFailedRelayoutEvent.class,
    VmNoCompatibleHostForSecondaryEvent.class,
    VmFailedToRebootGuestEvent.class,
    VmDisconnectedEvent.class,
    VmConfigMissingEvent.class,
    VmFailoverFailed.class,
    VmPoweredOffEvent.class,
    VmReloadFromPathFailedEvent.class,
    VmBeingMigratedEvent.class,
    VmReloadFromPathEvent.class,
    VmUuidChangedEvent.class,
    VmRemoteConsoleConnectedEvent.class,
    VmDasBeingResetEvent.class,
    NotEnoughResourcesToStartVmEvent.class,
    VmFaultToleranceStateChangedEvent.class,
    VmAcquiredTicketEvent.class,
    VmSecondaryAddedEvent.class,
    VmUpgradeCompleteEvent.class,
    VmPoweringOnWithCustomizedDVPortEvent.class,
    VmMaxFTRestartCountReached.class,
    VmFailedMigrateEvent.class,
    VmFailedToSuspendEvent.class,
    VmBeingCreatedEvent.class,
    VmFailedUpdatingSecondaryConfig.class,
    VmResettingEvent.class,
    VmInstanceUuidChangedEvent.class,
    VmRegisteredEvent.class,
    VmCreatedEvent.class,
    VmFailedToPowerOnEvent.class,
    VmWwnConflictEvent.class,
    VmGuestShutdownEvent.class,
    VmMessageWarningEvent.class,
    VmMessageEvent.class,
    VmResumingEvent.class,
    VmAcquiredMksTicketEvent.class,
    VmDasUpdateErrorEvent.class,
    VmStoppingEvent.class,
    VmWwnAssignedEvent.class,
    VmDasUpdateOkEvent.class,
    VmDasResetFailedEvent.class,
    VmEndRecordingEvent.class,
    VmGuestRebootEvent.class,
    VmReconfiguredEvent.class,
    VmEmigratingEvent.class,
    VmFailedToShutdownGuestEvent.class,
    VmInstanceUuidAssignedEvent.class,
    VmFailedToResetEvent.class,
    VmSecondaryDisabledBySystemEvent.class,
    VmSuspendingEvent.class,
    DrsRuleComplianceEvent.class,
    VmStartingSecondaryEvent.class,
    VmOrphanedEvent.class,
    VmFailedToPowerOffEvent.class,
    VmFailedToStandbyGuestEvent.class,
    VmUpgradeFailedEvent.class,
    VmMigratedEvent.class,
    VmRemovedEvent.class,
    VmDateRolledBackEvent.class,
    VmWwnChangedEvent.class,
    VmBeingHotMigratedEvent.class,
    VmEndReplayingEvent.class,
    VmRelocateSpecEvent.class,
    VmFaultToleranceVmTerminatedEvent.class,
    VmRemoteConsoleDisconnectedEvent.class,
    VmStartReplayingEvent.class,
    VmSecondaryStartedEvent.class,
    VmPoweredOnEvent.class,
    NoMaintenanceModeDrsRecommendationForVM.class,
    VmFailedStartingSecondaryEvent.class,
    VmBeingDeployedEvent.class,
    MigrationEvent.class,
    VmDiscoveredEvent.class,
    VmDiskFailedEvent.class,
    VmSecondaryEnabledEvent.class,
    VmPrimaryFailoverEvent.class,
    CustomizationEvent.class,
    VmMessageErrorEvent.class,
    VmCloneEvent.class,
    VmGuestStandbyEvent.class,
    VmNoNetworkAccessEvent.class,
    VmConnectedEvent.class,
    VmTimedoutStartingSecondaryEvent.class,
    VmMacAssignedEvent.class,
    VmStartingEvent.class
})
public class VmEvent
    extends Event
{

    protected boolean template;

    /**
     * Gets the value of the template property.
     * 
     */
    public boolean isTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     */
    public void setTemplate(boolean value) {
        this.template = value;
    }

}
