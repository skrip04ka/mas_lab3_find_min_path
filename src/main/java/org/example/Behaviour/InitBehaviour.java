package org.example.Behaviour;

import org.example.Model.ListData;
import org.example.Model.NodeData;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;

import java.util.List;
import java.util.Map;

public class InitBehaviour extends ParallelBehaviour {

    private final ListData data = new ListData();
    private final Map<String, Integer> neighbours;
    private final String findNodeName;

    public InitBehaviour(Map<String, Integer> neighbours, String findNodeName) {
        super(ParallelBehaviour.WHEN_ANY);
        this.neighbours = neighbours;
        this.findNodeName = findNodeName;
    }
    @Override
    public void onStart() {

        if (myAgent.getLocalName().equals(findNodeName)) {
            System.out.println("\ndude, are you seriously looking for yourself?");
            System.out.println("best path is you");
            myAgent.removeBehaviour(this);
        } else {
            addSubBehaviour(new InitRequestBehaviour(data, neighbours, findNodeName));
            addSubBehaviour(new WakerBehaviour(myAgent, 15000) {
                @Override
                protected void onWake() {
                }
            });
        }
    }

    @Override
    public int onEnd() {
        System.out.println("\n*-------------*\n");
        List<NodeData> minDataList = data.getMinDataAgreeList();
        if (!minDataList.isEmpty()) {
            System.out.println("found " + minDataList.size() + " minimum path(s):");
            for (NodeData minData: minDataList) {
                System.out.println("path: " + minData.getNodeNames().toString() + " totalLength = " + minData.getTotalLength());
            }
            System.out.println("\ntotal found " + data.getNodeDataAgreeSize() + " agree path(s)");
            System.out.println("total found " + data.getNodeDataRefuseSize() + " refused path(s)");
        } else {
            System.out.println("path not found");
        }

        return 1;
    }
}
