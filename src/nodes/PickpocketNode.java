package nodes;

import org.dreambot.api.input.event.impl.InteractionEvent;
import org.dreambot.api.input.event.impl.InteractionSetting;
import org.dreambot.api.input.mouse.destination.impl.EntityDestination;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;

import java.awt.Point;

import script.MasterFarmerPickpocketer;

import static org.dreambot.api.methods.MethodProvider.sleep;

public class PickpocketNode extends Node {

    public PickpocketNode(MasterFarmerPickpocketer main) {
        super(main);
    }

    @Override
    public boolean validate() {
        return !main.getInventory().isFull() &&
                !main.getLocalPlayer().isInCombat() &&
                playerInMasterFarmerArea();
    }

    @Override
    public int execute() {
        if (main.getShop() != null) {
            main.getShop().close();
        }
        boolean isMoving = main.masterFarmer.isMoving();
        if (isMoving) {
            rightClickPickpocket();
        } else {
            EntityDestination ed = new EntityDestination(main.getClient(), main.masterFarmer);
            InteractionEvent ie = new InteractionEvent(ed);
            ie.interact("Pickpocket", InteractionSetting.MOVE);
        }
        main.changeCameraAngle(getMasterFarmerRegion());
        return Calculations.random(200, 400);
    }

    private void rightClickPickpocket() {
        Point clickablePoint = main.masterFarmer.getClickablePoint();
        main.getMouse().click(clickablePoint, true);
        int x = clickablePoint.x;
        int y = clickablePoint.y;
        int randomXoffset = Calculations.random(11);
        boolean increaseX = Calculations.getRandom().nextBoolean();
        x = increaseX ? (x + randomXoffset) : (x - randomXoffset);
        int probabilityOfMisclick = Calculations.random(10);
        int randomYoffset;
        if (probabilityOfMisclick == 1) {
            final int ERROR_UPPER_BOUND_OFFSET = 34;
            final int ERROR_LOWER_BOUND_OFFSET = 41;
            randomYoffset = Calculations
                    .random(ERROR_UPPER_BOUND_OFFSET,
                            ERROR_LOWER_BOUND_OFFSET);
        } else {
            final int PICKPOCKET_OPTION_UPPER_BOUND_OFFSET = 19;
            final int PICKPOCKET_OPTION_LOWER_BOUND_OFFSET = 34;
            randomYoffset = Calculations
                    .random(PICKPOCKET_OPTION_UPPER_BOUND_OFFSET,
                            PICKPOCKET_OPTION_LOWER_BOUND_OFFSET);
        }
        y += randomYoffset;
        sleep(Calculations.random(50, 75));
        main.getMouse().click(new Point(x, y));
    }

    private MasterFarmerPickpocketer.Region getMasterFarmerRegion() {
        Tile tile = main.masterFarmer.getTile();
        if (main.CENTER_AREA.contains(tile)) {
            return MasterFarmerPickpocketer.Region.CENTER;
        }
        if (main.NORTH_WEST_1_AREA.contains(tile) || main.NORTH_WEST_2_AREA.contains(tile)) {
            return MasterFarmerPickpocketer.Region.NORTHWEST;
        }
        if (main.SOUTH_WEST_1_AREA.contains(tile) || main.SOUTH_WEST_2_AREA.contains(tile)) {
            return MasterFarmerPickpocketer.Region.SOUTHWEST;
        }
        if (main.SOUTH_EAST_1_AREA.contains(tile) || main.SOUTH_EAST_2_AREA.contains(tile)) {
            return MasterFarmerPickpocketer.Region.SOUTHEAST;
        }
        if (main.NORTH_EAST_1_AREA.contains(tile) || main.NORTH_EAST_2_AREA.contains(tile)) {
            return MasterFarmerPickpocketer.Region.NORTHEAST;
        }
        return MasterFarmerPickpocketer.Region.CENTER;
    }

    private boolean playerInMasterFarmerArea() {
        Tile playerTile = main.getLocalPlayer().getTile();
        return main.CENTER_AREA.contains(playerTile) ||
                main.SOUTH_WEST_1_AREA.contains(playerTile) ||
                main.SOUTH_WEST_2_AREA.contains(playerTile) ||
                main.SOUTH_EAST_1_AREA.contains(playerTile) ||
                main.SOUTH_EAST_2_AREA.contains(playerTile) ||
                main.NORTH_WEST_1_AREA.contains(playerTile) ||
                main.NORTH_WEST_2_AREA.contains(playerTile) ||
                main.NORTH_EAST_1_AREA.contains(playerTile) ||
                main.NORTH_EAST_2_AREA.contains(playerTile);
    }
}
