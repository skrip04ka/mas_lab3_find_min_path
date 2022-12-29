package org.example.Agents;

import org.example.AutorunnableAgent;
import org.example.DfHelper;
import org.example.Behaviour.InitBehaviour;
import org.example.Behaviour.ProRequestBehaviour;
import org.example.Model.CfgClass;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@AutorunnableAgent(name = "N", starIndex = 1, count = 12)
@Slf4j
public class NodeAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.registerAgent(this,  getLocalName());
        log.debug("Agent <{}> is init", getLocalName());

        CfgClass cfg;
        try {
            JAXBContext context = JAXBContext.newInstance(CfgClass.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            cfg = (CfgClass) jaxbUnmarshaller.unmarshal(
                    new File("src/main/resources/Node/"  + getLocalName() + ".xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        if (cfg.isStart()) {
            log.info("Node {} find {}", getLocalName(), cfg.getFindNodeName());
            addBehaviour(new InitBehaviour(cfg.getNeighbours(), cfg.getFindNodeName()));
        }

        addBehaviour(new ProRequestBehaviour(cfg.getNeighbours()));

    }

}
