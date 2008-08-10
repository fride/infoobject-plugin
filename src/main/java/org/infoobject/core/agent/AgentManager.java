package org.infoobject.core.agent;

import org.apache.commons.collections15.map.MultiKeyMap;

import java.util.Map;
import java.util.HashMap;

/**
 * <p>
 * Class AgentManager ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 16:11:18
 */
public class AgentManager {
    private Map<String, Agent> agents = new HashMap<String, Agent>();

    public boolean remove(Agent agent) {
        return null == agents.remove(agent.getId());        
    }
    public Agent get(String id) {
        return get(id, false);
    }

    public Agent get(String id, boolean create) {
        Agent agent = agents.get(id);
        if (agent == null && create) {
            agent = new Agent(id);
            agents.put(id, agent);
        }
        return agent;
    }
}
