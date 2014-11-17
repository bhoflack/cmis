
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClusterDasAdvancedRuntimeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClusterDasAdvancedRuntimeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="dasHostInfo" type="{urn:vim25}ClusterDasHostInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClusterDasAdvancedRuntimeInfo", propOrder = {
    "dasHostInfo"
})
@XmlSeeAlso({
    ClusterDasFailoverLevelAdvancedRuntimeInfo.class
})
public class ClusterDasAdvancedRuntimeInfo
    extends DynamicData
{

    protected ClusterDasHostInfo dasHostInfo;

    /**
     * Gets the value of the dasHostInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterDasHostInfo }
     *     
     */
    public ClusterDasHostInfo getDasHostInfo() {
        return dasHostInfo;
    }

    /**
     * Sets the value of the dasHostInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterDasHostInfo }
     *     
     */
    public void setDasHostInfo(ClusterDasHostInfo value) {
        this.dasHostInfo = value;
    }

}
