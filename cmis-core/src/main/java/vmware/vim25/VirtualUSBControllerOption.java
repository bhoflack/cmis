
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VirtualUSBControllerOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VirtualUSBControllerOption">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}VirtualControllerOption">
 *       &lt;sequence>
 *         &lt;element name="autoConnectDevices" type="{urn:vim25}BoolOption"/>
 *         &lt;element name="ehciSupported" type="{urn:vim25}BoolOption"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualUSBControllerOption", propOrder = {
    "autoConnectDevices",
    "ehciSupported"
})
public class VirtualUSBControllerOption
    extends VirtualControllerOption
{

    @XmlElement(required = true)
    protected BoolOption autoConnectDevices;
    @XmlElement(required = true)
    protected BoolOption ehciSupported;

    /**
     * Gets the value of the autoConnectDevices property.
     * 
     * @return
     *     possible object is
     *     {@link BoolOption }
     *     
     */
    public BoolOption getAutoConnectDevices() {
        return autoConnectDevices;
    }

    /**
     * Sets the value of the autoConnectDevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoolOption }
     *     
     */
    public void setAutoConnectDevices(BoolOption value) {
        this.autoConnectDevices = value;
    }

    /**
     * Gets the value of the ehciSupported property.
     * 
     * @return
     *     possible object is
     *     {@link BoolOption }
     *     
     */
    public BoolOption getEhciSupported() {
        return ehciSupported;
    }

    /**
     * Sets the value of the ehciSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoolOption }
     *     
     */
    public void setEhciSupported(BoolOption value) {
        this.ehciSupported = value;
    }

}
