
package vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplyProfile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplyProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="policy" type="{urn:vim25}ProfilePolicy" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplyProfile", propOrder = {
    "enabled",
    "policy"
})
@XmlSeeAlso({
    DvsProfile.class,
    FirewallProfile.class,
    NasStorageProfile.class,
    StaticRouteProfile.class,
    HostMemoryProfile.class,
    NetworkProfile.class,
    ServiceProfile.class,
    VirtualSwitchSelectionProfile.class,
    UserProfile.class,
    StorageProfile.class,
    VlanProfile.class,
    PnicUplinkProfile.class,
    AuthenticationProfile.class,
    DateTimeProfile.class,
    PermissionProfile.class,
    IpAddressProfile.class,
    PhysicalNicProfile.class,
    ActiveDirectoryProfile.class,
    FirewallProfileRulesetProfile.class,
    NumPortsProfile.class,
    LinkProfile.class,
    SecurityProfile.class,
    NetworkPolicyProfile.class,
    UserGroupProfile.class,
    VirtualSwitchProfile.class,
    HostApplyProfile.class,
    DvsVNicProfile.class,
    OptionProfile.class,
    PortGroupProfile.class,
    IpRouteProfile.class,
    NetworkProfileDnsConfigProfile.class
})
public class ApplyProfile
    extends DynamicData
{

    protected boolean enabled;
    protected List<ProfilePolicy> policy;

    /**
     * Gets the value of the enabled property.
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the policy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfilePolicy }
     * 
     * 
     */
    public List<ProfilePolicy> getPolicy() {
        if (policy == null) {
            policy = new ArrayList<ProfilePolicy>();
        }
        return this.policy;
    }

}
