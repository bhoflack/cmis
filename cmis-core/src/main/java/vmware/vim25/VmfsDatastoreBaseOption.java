
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VmfsDatastoreBaseOption complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VmfsDatastoreBaseOption">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="layout" type="{urn:vim25}HostDiskPartitionLayout"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VmfsDatastoreBaseOption", propOrder = {
    "layout"
})
@XmlSeeAlso({
    VmfsDatastoreSingleExtentOption.class,
    VmfsDatastoreMultipleExtentOption.class
})
public class VmfsDatastoreBaseOption
    extends DynamicData
{

    @XmlElement(required = true)
    protected HostDiskPartitionLayout layout;

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link HostDiskPartitionLayout }
     *     
     */
    public HostDiskPartitionLayout getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link HostDiskPartitionLayout }
     *     
     */
    public void setLayout(HostDiskPartitionLayout value) {
        this.layout = value;
    }

}
