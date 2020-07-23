package nodes;

import script.MasterFarmerPickpocketer;

public abstract class Node {

    protected final MasterFarmerPickpocketer main;

    public Node(MasterFarmerPickpocketer main) {
        this.main = main;
    }

    public abstract boolean validate();

    public abstract int execute();
}
