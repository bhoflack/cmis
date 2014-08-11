
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DvsEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DvsEvent">
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
@XmlType(name = "DvsEvent")
@XmlSeeAlso({
    DvsPortDeletedEvent.class,
    DvsPortConnectedEvent.class,
    OutOfSyncDvsHost.class,
    DvsUpgradeAvailableEvent.class,
    DvsUpgradedEvent.class,
    DvsPortUnblockedEvent.class,
    DvsHostBackInSyncEvent.class,
    DvsPortLinkDownEvent.class,
    DvsPortExitedPassthruEvent.class,
    DvsPortBlockedEvent.class,
    DvsUpgradeInProgressEvent.class,
    DvsCreatedEvent.class,
    DvsRenamedEvent.class,
    DvsHostStatusUpdated.class,
    DvsPortLeavePortgroupEvent.class,
    DvsHostLeftEvent.class,
    DvsPortCreatedEvent.class,
    DvsReconfiguredEvent.class,
    DvsHostWentOutOfSyncEvent.class,
    DvsPortDisconnectedEvent.class,
    DvsDestroyedEvent.class,
    DvsPortJoinPortgroupEvent.class,
    DvsHostJoinedEvent.class,
    DvsPortLinkUpEvent.class,
    DvsPortEnteredPassthruEvent.class,
    DvsPortReconfiguredEvent.class,
    DvsUpgradeRejectedEvent.class,
    DvsMergedEvent.class
})
public class DvsEvent
    extends Event
{


}
