package org.example.Behaviour;

import org.example.DfHelper;
import org.example.Model.ListData;
import org.example.Model.NodeData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class InitRequestBehaviour extends Behaviour {

    private final ListData data;
    private final Map<String, Integer> neighbours;
    private final String findNodeName;

    private final MessageTemplate mt = MessageTemplate.or(
            MessageTemplate.MatchPerformative(ACLMessage.AGREE),
            MessageTemplate.MatchPerformative(ACLMessage.REFUSE)
    );

    public InitRequestBehaviour(ListData data, Map<String, Integer> neighbours, String findNodeName) {
        this.data = data;
        this.neighbours = neighbours;
        this.findNodeName = findNodeName;
    }

    @Override
    public void onStart() {
        for (String node : neighbours.keySet()) {
            ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
            DfHelper.findAgents(myAgent, node).forEach(m::addReceiver);
            NodeData nodeData = new NodeData(myAgent.getLocalName(), findNodeName);
            nodeData.addData(node, neighbours.get(node));
            m.setContent(NodeData.dataToString(nodeData));
            myAgent.send(m);
            log.debug("send to <{}> with content <{}>", node, m.getContent());
        }
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            NodeData nd = NodeData.parseData(msg.getContent());
            if (msg.getPerformative() == ACLMessage.AGREE) {
                log.info("found a new path {} length {}", nd.getNodeNames().toString(), nd.getTotalLength());
                data.addDataAgree(nd);
            } else {
                log.info("path {} aborted, len {}", nd.getNodeNames().toString(), nd.getTotalLength());
                data.addDataRefuse(nd);
            }

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
