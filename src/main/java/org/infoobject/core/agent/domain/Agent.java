package org.infoobject.core.agent.domain;

/**
 * <p>
 * Class Agent ZUSAMMENFASSUNG
 * </p>
 * <p>
 * DETAILS
 * </p>
 *
 * @author Jan Friderici
 *         Date: 09.08.2008
 *         Time: 00:21:41
 */
public class Agent implements Comparable<Agent>{
    private String id;
    private String userName;
    private boolean admin;
    private boolean guest;
    private String jabberID;
    public static final Agent ANONYMOUS = new Agent("anonymous");

    public Agent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public String getJabberID() {
        return jabberID;
    }

    public void setJabberID(String jabberID) {
        this.jabberID = jabberID;
    }

    public int compareTo(Agent agent) {
        if (agent == null) return 1;
        if (agent == ANONYMOUS) return 1;
        if (getId() == null && agent.getId() == null) return 0;
        if (getId() == null) return -1;
        if (agent.getId() == null) return 1;
        else return getId().compareTo(agent.getId());
    }
}
