
package vmware.vim25;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SnapshotCopyNotSupported complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SnapshotCopyNotSupported">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:vim25}MigrationFault">
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
@XmlType(name = "SnapshotCopyNotSupported")
@XmlSeeAlso({
    SnapshotMoveNotSupported.class,
    SnapshotCloneNotSupported.class,
    HotSnapshotMoveNotSupported.class,
    SnapshotMoveFromNonHomeNotSupported.class,
    SnapshotMoveToNonHomeNotSupported.class
})
public class SnapshotCopyNotSupported
    extends MigrationFault
{


}
