package nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;

import script.MasterFarmerPickpocketer;

public class Break extends Node {
    public Break(MasterFarmerPickpocketer main) {
        super(main);
    }

    @Override
    public boolean validate() {
        int probabilityOfTakingABreak = Calculations.random(6000);
        return probabilityOfTakingABreak == 0 && !main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        main.getMouse().moveMouseOutsideScreen();
        int oneMinute = 1000 * 60;
        int threeMinutes = 1000 * 60 * 3;
        MethodProvider.sleep(oneMinute, threeMinutes);
        return 0;
    }
}
