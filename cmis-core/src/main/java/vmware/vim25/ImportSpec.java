
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="entityConfig" type="{urn:vim25}VAppEntityConfigInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportSpec", propOrder = {
    "entityConfig"
})
@XmlSeeAlso({
    VirtualAppImportSpec.class,
    VirtualMachineImportSpec.class
})
public class ImportSpec
    extends DynamicData
{

    protected VAppEntityConfigInfo entityConfig;

    /**
     * Gets the value of the entityConfig property.
     * 
     * @return
     *     possible object is
     *     {@link VAppEntityConfigInfo }
     *     
     */
    public VAppEntityConfigInfo getEntityConfig() {
        return entityConfig;
    }

    /**
     * Sets the value of the entityConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link VAppEntityConfigInfo }
     *     
     */
    public void setEntityConfig(VAppEntityConfigInfo value) {
        this.entityConfig = value;
    }

}
