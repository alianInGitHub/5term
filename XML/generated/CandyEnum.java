//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.06 at 01:45:50 AM EET 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for candyEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="candyEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CANDY"/>
 *     &lt;enumeration value="NAME"/>
 *     &lt;enumeration value="ENERGY"/>
 *     &lt;enumeration value="TYPE"/>
 *     &lt;enumeration value="INGREDIENT"/>
 *     &lt;enumeration value="VALUE"/>
 *     &lt;enumeration value="PRODUCTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "candyEnum")
@XmlEnum
public enum CandyEnum {

    CANDY,
    NAME,
    ENERGY,
    TYPE,
    INGREDIENT,
    VALUE,
    PRODUCTION;

    public String value() {
        return name();
    }

    public static CandyEnum fromValue(String v) {
        return valueOf(v);
    }

}
