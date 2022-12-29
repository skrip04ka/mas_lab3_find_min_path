package org.example.Behaviour;

import org.example.DfHelper;
import org.example.Model.NodeData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SendResultBehaviour extends Behaviour {

    private NodeData nodeData;
    private int acl;

    public SendResultBehaviour(NodeData nodeData, int acl) {
        this.nodeData = nodeData;
        this.acl = acl;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(acl);
        DfHelper.findAgents(myAgent, nodeData.firstNodeName()).forEach(msg::addReceiver);
        msg.setContent(NodeData.dataToString(nodeData));
        myAgent.send(msg);
        log.debug("send to <{}> with content <{}>", nodeData.firstNodeName(), msg.getContent());
    }

    @Override
    public boolean done() {
        return true;
    }
}
