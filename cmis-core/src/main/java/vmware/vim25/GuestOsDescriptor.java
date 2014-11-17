
package vmware.vim25;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GuestOsDescriptor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuestOsDescriptor">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}DynamicData">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="family" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supportedMaxCPUs" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supportedMinMemMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supportedMaxMemMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="recommendedMemMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="recommendedColorDepth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supportedDiskControllerList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="recommendedSCSIController" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recommendedDiskController" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="supportedNumDisks" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="recommendedDiskSizeMB" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supportedEthernetCard" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="recommendedEthernetCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supportsSlaveDisk" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="cpuFeatureMask" type="{urn:vim25}HostCpuIdInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supportsWakeOnLan" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="supportsVMI" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportsMemoryHotAdd" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportsCpuHotAdd" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="supportsCpuHotRemove" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestOsDescriptor", propOrder = {
    "id",
    "family",
    "fullName",
    "supportedMaxCPUs",
    "supportedMinMemMB",
    "supportedMaxMemMB",
    "recommendedMemMB",
    "recommendedColorDepth",
    "supportedDiskControllerList",
    "recommendedSCSIController",
    "recommendedDiskController",
    "supportedNumDisks",
    "recommendedDiskSizeMB",
    "supportedEthernetCard",
    "recommendedEthernetCard",
    "supportsSlaveDisk",
    "cpuFeatureMask",
    "supportsWakeOnLan",
    "supportsVMI",
    "supportsMemoryHotAdd",
    "supportsCpuHotAdd",
    "supportsCpuHotRemove"
})
public class GuestOsDescriptor
    extends DynamicData
{

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String family;
    @XmlElement(required = true)
    protected String fullName;
    protected int supportedMaxCPUs;
    protected int supportedMinMemMB;
    protected int supportedMaxMemMB;
    protected int recommendedMemMB;
    protected int recommendedColorDepth;
    @XmlElement(required = true)
    protected List<String> supportedDiskControllerList;
    protected String recommendedSCSIController;
    @XmlElement(required = true)
    protected String recommendedDiskController;
    protected int supportedNumDisks;
    protected int recommendedDiskSizeMB;
    @XmlElement(required = true)
    protected List<String> supportedEthernetCard;
    protected String recommendedEthernetCard;
    protected Boolean supportsSlaveDisk;
    protected List<HostCpuIdInfo> cpuFeatureMask;
    protected boolean supportsWakeOnLan;
    protected Boolean supportsVMI;
    protected Boolean supportsMemoryHotAdd;
    protected Boolean supportsCpuHotAdd;
    protected Boolean supportsCpuHotRemove;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the family property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamily(String value) {
        this.family = value;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the supportedMaxCPUs property.
     * 
     */
    public int getSupportedMaxCPUs() {
        return supportedMaxCPUs;
    }

    /**
     * Sets the value of the supportedMaxCPUs property.
     * 
     */
    public void setSupportedMaxCPUs(int value) {
        this.supportedMaxCPUs = value;
    }

    /**
     * Gets the value of the supportedMinMemMB property.
     * 
     */
    public int getSupportedMinMemMB() {
        return supportedMinMemMB;
    }

    /**
     * Sets the value of the supportedMinMemMB property.
     * 
     */
    public void setSupportedMinMemMB(int value) {
        this.supportedMinMemMB = value;
    }

    /**
     * Gets the value of the supportedMaxMemMB property.
     * 
     */
    public int getSupportedMaxMemMB() {
        return supportedMaxMemMB;
    }

    /**
     * Sets the value of the supportedMaxMemMB property.
     * 
     */
    public void setSupportedMaxMemMB(int value) {
        this.supportedMaxMemMB = value;
    }

    /**
     * Gets the value of the recommendedMemMB property.
     * 
     */
    public int getRecommendedMemMB() {
        return recommendedMemMB;
    }

    /**
     * Sets the value of the recommendedMemMB property.
     * 
     */
    public void setRecommendedMemMB(int value) {
        this.recommendedMemMB = value;
    }

    /**
     * Gets the value of the recommendedColorDepth property.
     * 
     */
    public int getRecommendedColorDepth() {
        return recommendedColorDepth;
    }

    /**
     * Sets the value of the recommendedColorDepth property.
     * 
     */
    public void setRecommendedColorDepth(int value) {
        this.recommendedColorDepth = value;
    }

    /**
     * Gets the value of the supportedDiskControllerList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedDiskControllerList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedDiskControllerList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSupportedDiskControllerList() {
        if (supportedDiskControllerList == null) {
            supportedDiskControllerList = new ArrayList<String>();
        }
        return this.supportedDiskControllerList;
    }

    /**
     * Gets the value of the recommendedSCSIController property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendedSCSIController() {
        return recommendedSCSIController;
    }

    /**
     * Sets the value of the recommendedSCSIController property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendedSCSIController(String value) {
        this.recommendedSCSIController = value;
    }

    /**
     * Gets the value of the recommendedDiskController property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendedDiskController() {
        return recommendedDiskController;
    }

    /**
     * Sets the value of the recommendedDiskController property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendedDiskController(String value) {
        this.recommendedDiskController = value;
    }

    /**
     * Gets the value of the supportedNumDisks property.
     * 
     */
    public int getSupportedNumDisks() {
        return supportedNumDisks;
    }

    /**
     * Sets the value of the supportedNumDisks property.
     * 
     */
    public void setSupportedNumDisks(int value) {
        this.supportedNumDisks = value;
    }

    /**
     * Gets the value of the recommendedDiskSizeMB property.
     * 
     */
    public int getRecommendedDiskSizeMB() {
        return recommendedDiskSizeMB;
    }

    /**
     * Sets the value of the recommendedDiskSizeMB property.
     * 
     */
    public void setRecommendedDiskSizeMB(int value) {
        this.recommendedDiskSizeMB = value;
    }

    /**
     * Gets the value of the supportedEthernetCard property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedEthernetCard property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedEthernetCard().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSupportedEthernetCard() {
        if (supportedEthernetCard == null) {
            supportedEthernetCard = new ArrayList<String>();
        }
        return this.supportedEthernetCard;
    }

    /**
     * Gets the value of the recommendedEthernetCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendedEthernetCard() {
        return recommendedEthernetCard;
    }

    /**
     * Sets the value of the recommendedEthernetCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendedEthernetCard(String value) {
        this.recommendedEthernetCard = value;
    }

    /**
     * Gets the value of the supportsSlaveDisk property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupportsSlaveDisk() {
        return supportsSlaveDisk;
    }

    /**
     * Sets the value of the supportsSlaveDisk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupportsSlaveDisk(Boolean value) {
        this.supportsSlaveDisk = value;
    }

    /**
     * Gets the value of the cpuFeatureMask property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cpuFeatureMask property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCpuFeatureMask().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostCpuIdInfo }
     * 
     * 
     */
    public List<HostCpuIdInfo> getCpuFeatureMask() {
        if (cpuFeatureMask == null) {
            cpuFeatureMask = new ArrayList<HostCpuIdInfo>();
        }
        return this.cpuFeatureMask;
    }

    /**
     * Gets the value of the supportsWakeOnLan property.
     * 
     */
    public boolean isSupportsWakeOnLan() {
        return supportsWakeOnLan;
    }

    /**
     * Sets the value of the supportsWakeOnLan property.
     * 
     */
    public void setSupportsWakeOnLan(boolean value) {
        this.supportsWakeOnLan = value;
    }

    /**
     * Gets the value of the supportsVMI property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupportsVMI() {
        return supportsVMI;
    }

    /**
     * Sets the value of the supportsVMI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupportsVMI(Boolean value) {
        this.supportsVMI = value;
    }

    /**
     * Gets the value of the supportsMemoryHotAdd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupportsMemoryHotAdd() {
        return supportsMemoryHotAdd;
    }

    /**
     * Sets the value of the supportsMemoryHotAdd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupportsMemoryHotAdd(Boolean value) {
        this.supportsMemoryHotAdd = value;
    }

    /**
     * Gets the value of the supportsCpuHotAdd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupportsCpuHotAdd() {
        return supportsCpuHotAdd;
    }

    /**
     * Sets the value of the supportsCpuHotAdd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupportsCpuHotAdd(Boolean value) {
        this.supportsCpuHotAdd = value;
    }

    /**
     * Gets the value of the supportsCpuHotRemove property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupportsCpuHotRemove() {
        return supportsCpuHotRemove;
    }

    /**
     * Sets the value of the supportsCpuHotRemove property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupportsCpuHotRemove(Boolean value) {
        this.supportsCpuHotRemove = value;
    }

}
