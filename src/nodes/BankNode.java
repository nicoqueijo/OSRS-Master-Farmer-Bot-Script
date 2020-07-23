package nodes;

import org.dreambot.api.methods.Calculations;

import script.MasterFarmerPickpocketer;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class BankNode extends Node {

    public BankNode(MasterFarmerPickpocketer main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return main.getInventory().isFull() || main.getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        log("Banking.");
        sleepUntil(() -> main.getWalking().walk(main.INSIDE_BANK_AREA.getRandomTile()), Calculations.random(2000, 3000));
        sleep(Calculations.random(600, 1000));
        main.changeCameraAngle(MasterFarmerPickpocketer.Region.NORTHEAST);
        sleep(Calculations.random(600, 1000));
        sleepUntil(() -> main.getBank().open(), Calculations.random(2000, 3000));
        sleep(Calculations.random(300, 600));
        sleepUntil(() -> main.getBank().depositAllItems(), Calculations.random(2000, 3000));
        sleep(Calculations.random(300, 600));
        sleepUntil(() -> main.getWalking().walk(main.CENTER_AREA.getRandomTile()), Calculations.random(2000, 3000));
        return Calculations.random(1000, 2000);
    }
}
