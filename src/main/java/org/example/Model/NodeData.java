package org.example.Model;

import org.example.JsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class NodeData {
    private List<String> nodeNames = new ArrayList<>();
    private int totalLength = 0;
    private String findNodeName;

    public NodeData(String firstNodeName, String findNodeName){
        this.nodeNames.add(firstNodeName);
        this.findNodeName = findNodeName;
    };

    public String firstNodeName() {
        return nodeNames.get(0);
    }

    public void addData(String nodeName, int pathLength) {
        this.nodeNames.add(nodeName);
        totalLength = totalLength + pathLength;
    }

    public static NodeData parseData(String dataString) {
        return JsonParser.parseData(dataString, NodeData.class);
    }

    public static String dataToString(NodeData nodeData) {
        return JsonParser.dataToString(nodeData);
    }



}
