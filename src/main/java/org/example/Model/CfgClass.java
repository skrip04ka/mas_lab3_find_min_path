package org.example.Model;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
@Getter
public class CfgClass {
    @XmlElement
    private boolean start = false;

    @XmlElement
    private String findNodeName;

    @XmlElement()
    private Map<String, Integer> neighbours = new HashMap<>();

}
