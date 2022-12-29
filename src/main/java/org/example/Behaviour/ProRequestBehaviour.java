package org.example.Behaviour;

import org.example.DfHelper;
import org.example.Model.NodeData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ProRequestBehaviour extends Behaviour {

    private final Map<String, Integer> neighbours;
    public ProRequestBehaviour(Map<String, Integer> neighbours){
        this.neighbours = neighbours;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        if (msg != null) {
            log.debug("received inform from <{}> with content <{}>", msg.getSender().getLocalName(), msg.getContent());
            NodeData nodeData = NodeData.parseData(msg.getContent());

            if (nodeData.getFindNodeName().equals(myAgent.getLocalName())) {
                myAgent.addBehaviour(new SendResultBehaviour(nodeData, ACLMessage.AGREE));
            } else if (neighbours.size() == 1) {
                myAgent.addBehaviour(new SendResultBehaviour(nodeData, ACLMessage.REFUSE));
            }  else {
                for (String nodeName : neighbours.keySet()) {
                    NodeData nd = NodeData.parseData(msg.getContent());
                    if (nd.getNodeNames().contains(nodeName)) {
                        continue;
                    }
                    ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
                    DfHelper.findAgents(myAgent, nodeName).forEach(m::addReceiver);
                    nd.addData(nodeName, neighbours.get(nodeName));
                    m.setContent(NodeData.dataToString(nd));
                    myAgent.send(m);
                    log.debug("send to <{}> with content <{}>", nodeName, m.getContent());
                }
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
