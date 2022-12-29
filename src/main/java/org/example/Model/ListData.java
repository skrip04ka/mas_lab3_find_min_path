package org.example.Model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListData {
    private final List<NodeData> nodeDataAgree;
    private final List<NodeData> nodeDataRefuse;

    public ListData() {
        nodeDataAgree = new ArrayList<>();
        nodeDataRefuse = new ArrayList<>();
    }

    public void addDataAgree(NodeData nodeData){
        nodeDataAgree.add(nodeData);
    }
    public void addDataRefuse(NodeData nodeData){
        nodeDataRefuse.add(nodeData);
    }

    public List<NodeData> getMinDataAgreeList(){
        int minLength = 0;
        List<NodeData> minDataList = new ArrayList<>();
        for (NodeData nd : nodeDataAgree) {
            if (minDataList.isEmpty() || nd.getTotalLength() < minLength) {
                minDataList.clear();
                minDataList.add(nd);
                minLength = nd.getTotalLength();

            } else if (nd.getTotalLength() == minLength) {
                minDataList.add(nd);
            }
        }
        return minDataList;
    }

    public int getNodeDataRefuseSize() {
        return nodeDataRefuse.size();
    }

    public int getNodeDataAgreeSize() {
        return nodeDataAgree.size();
    }

}
