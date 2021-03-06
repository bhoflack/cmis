
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmCloneFailedEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmCloneFailedEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VmCloneEvent">
 *       &lt;sequence>
 *         &lt;element name="destFolder" type="{urn:vim25}FolderEventArgument"/>
 *         &lt;element name="destName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destHost" type="{urn:vim25}HostEventArgument"/>
 *         &lt;element name="reason" type="{urn:vim25}LocalizedMethodFault"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmCloneFailedEvent", propOrder = {
    "destFolder",
    "destName",
    "destHost",
    "reason"
})
public class VmCloneFailedEvent
    extends VmCloneEvent
{

    @XmlElement(required = true)
    protected FolderEventArgument destFolder;
    @XmlElement(required = true)
    protected String destName;
    @XmlElement(required = true)
    protected HostEventArgument destHost;
    @XmlElement(required = true)
    protected LocalizedMethodFault reason;

    /**
     * Gets the value of the destFolder property.
     * 
     * @return
     *     possible object is
     *     {@link FolderEventArgument }
     *     
     */
    public FolderEventArgument getDestFolder() {
        return destFolder;
    }

    /**
     * Sets the value of the destFolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link FolderEventArgument }
     *     
     */
    public void setDestFolder(FolderEventArgument value) {
        this.destFolder = value;
    }

    /**
     * Gets the value of the destName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestName() {
        return destName;
    }

    /**
     * Sets the value of the destName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestName(String value) {
        this.destName = value;
    }

    /**
     * Gets the value of the destHost property.
     * 
     * @return
     *     possible object is
     *     {@link HostEventArgument }
     *     
     */
    public HostEventArgument getDestHost() {
        return destHost;
    }

    /**
     * Sets the value of the destHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostEventArgument }
     *     
     */
    public void setDestHost(HostEventArgument value) {
        this.destHost = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public LocalizedMethodFault getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizedMethodFault }
     *     
     */
    public void setReason(LocalizedMethodFault value) {
        this.reason = value;
    }

}
