package script;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.ArrayList;
import java.util.List;

import nodes.BankNode;
import nodes.Break;
import nodes.Node;
import nodes.PickpocketNode;

@ScriptManifest(
        author = "Nico",
        description = "Pickpockets master farmer",
        category = Category.THIEVING,
        version = 1.0,
        name = "Master Farmer Pickpocketer"
)
public class MasterFarmerPickpocketer extends AbstractScript {
    
    public enum Region {
        CENTER, SOUTHWEST, NORTHWEST, NORTHEAST, SOUTHEAST
    }

    private final Tile INSIDE_BANK_TOP_RIGHT = new Tile(3093, 3245, 0);
    private final Tile INSIDE_BANK_BOTTOM_LEFT = new Tile(3092, 3243, 0);
    public final Area INSIDE_BANK_AREA = new Area(INSIDE_BANK_TOP_RIGHT, INSIDE_BANK_BOTTOM_LEFT);

    private final Tile CENTER_TOP_RIGHT = new Tile(3082, 3252, 0);
    private final Tile CENTER_BOTTOM_LEFT = new Tile(3077, 3248, 0);
    public final Area CENTER_AREA = new Area(CENTER_TOP_RIGHT, CENTER_BOTTOM_LEFT);

    private final Tile SOUTH_WEST_1_TOP_RIGHT = new Tile(3076, 3249, 0);
    private final Tile SOUTH_WEST_1_BOTTOM_LEFT = new Tile(3072, 3248, 0);
    public final Area SOUTH_WEST_1_AREA = new Area(SOUTH_WEST_1_TOP_RIGHT, SOUTH_WEST_1_BOTTOM_LEFT);

    private final Tile SOUTH_WEST_2_TOP_RIGHT = new Tile(3078, 3247, 0);
    private final Tile SOUTH_WEST_2_BOTTOM_LEFT = new Tile(3072, 3244, 0);
    public final Area SOUTH_WEST_2_AREA = new Area(SOUTH_WEST_2_TOP_RIGHT, SOUTH_WEST_2_BOTTOM_LEFT);

    private final Tile NORTH_WEST_1_TOP_RIGHT = new Tile(3079, 3258, 0);
    private final Tile NORTH_WEST_1_BOTTOM_LEFT = new Tile(3072, 3253, 0);
    public final Area NORTH_WEST_1_AREA = new Area(NORTH_WEST_1_TOP_RIGHT, NORTH_WEST_1_BOTTOM_LEFT);

    private final Tile NORTH_WEST_2_TOP_RIGHT = new Tile(3076, 3252, 0);
    private final Tile NORTH_WEST_2_BOTTOM_LEFT = new Tile(3072, 3250, 0);
    public final Area NORTH_WEST_2_AREA = new Area(NORTH_WEST_2_TOP_RIGHT, NORTH_WEST_2_BOTTOM_LEFT);

    private final Tile NORTH_EAST_1_TOP_RIGHT = new Tile(3086, 3258, 0);
    private final Tile NORTH_EAST_1_BOTTOM_LEFT = new Tile(3080, 3253, 0);
    public final Area NORTH_EAST_1_AREA = new Area(NORTH_EAST_1_TOP_RIGHT, NORTH_EAST_1_BOTTOM_LEFT);

    private final Tile NORTH_EAST_2_TOP_RIGHT = new Tile(3086, 3252, 0);
    private final Tile NORTH_EAST_2_BOTTOM_LEFT = new Tile(3083, 3251, 0);
    public final Area NORTH_EAST_2_AREA = new Area(NORTH_EAST_2_TOP_RIGHT, NORTH_EAST_2_BOTTOM_LEFT);

    private final Tile SOUTH_EAST_1_TOP_RIGHT = new Tile(3086, 3250, 0);
    private final Tile SOUTH_EAST_1_BOTTOM_LEFT = new Tile(3083, 3248, 0);
    public final Area SOUTH_EAST_1_AREA = new Area(SOUTH_EAST_1_TOP_RIGHT, SOUTH_EAST_1_BOTTOM_LEFT);

    private final Tile SOUTH_EAST_2_TOP_RIGHT = new Tile(3086, 3247, 0);
    private final Tile SOUTH_EAST_2_BOTTOM_LEFT = new Tile(3079, 3244, 0);
    public final Area SOUTH_EAST_2_AREA = new Area(SOUTH_EAST_2_TOP_RIGHT, SOUTH_EAST_2_BOTTOM_LEFT);

    public NPC masterFarmer;
    private Region region = Region.CENTER;

    private List<Node> nodes;

    @Override
    public int onLoop() {
        for (Node node : nodes) {
            if (node.validate()) {
                return node.execute();
            }
        }
        getWalking().walk(CENTER_AREA.getRandomTile());
        return Calculations.random(3000, 4000);
    }

    @Override
    public void onStart() {
        int MASTER_FARMER_ID = 5730;
        masterFarmer = getNpcs().closest(MASTER_FARMER_ID);
        sleep(Calculations.random(1000, 1500));
        getTabs().openWithMouse(Tab.INVENTORY);
        nodes = new ArrayList<>();
        nodes.add(new Break(this));
        nodes.add(new PickpocketNode(this));
        nodes.add(new BankNode(this));
    }

    public void changeCameraAngle(Region region) {
        if (this.region == region) {
            return;
        }
        log("Master farmer changed region: " + this.region.name() + " -> " + region.name());
        this.region = region;
        int yaw;
        int pitch;
        int MIN_CAMERA_PITCH = 128;
        int MID_CAMERA_PITCH = 255;
        int MAX_CAMERA_PITCH = 383;
        int NORTH_CAMERA_YAW = 0;
        int WEST_CAMERA_YAW = 500;
        int SOUTH_CAMERA_YAW = 1000;
        int EAST_CAMERA_YAW = 1500;
        int MAX_CAMERA_YAW = 2000;
        switch (region) {
            case CENTER: {
                yaw = getCamera().getYaw();
                pitch = MAX_CAMERA_PITCH;
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
            case SOUTHWEST: {
                yaw = Calculations.random(EAST_CAMERA_YAW, MAX_CAMERA_YAW);
                pitch = Calculations.random(MIN_CAMERA_PITCH, MID_CAMERA_PITCH);
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
            case NORTHWEST: {
                yaw = Calculations.random(SOUTH_CAMERA_YAW, EAST_CAMERA_YAW);
                pitch = Calculations.random(MIN_CAMERA_PITCH, MID_CAMERA_PITCH);
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
            case NORTHEAST: {
                yaw = Calculations.random(WEST_CAMERA_YAW, SOUTH_CAMERA_YAW);
                pitch = Calculations.random(MID_CAMERA_PITCH, MAX_CAMERA_PITCH);
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
            case SOUTHEAST: {
                yaw = Calculations.random(NORTH_CAMERA_YAW, WEST_CAMERA_YAW);
                pitch = Calculations.random(MID_CAMERA_PITCH, MAX_CAMERA_PITCH);
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
            default: {
                yaw = getCamera().getYaw();
                pitch = getCamera().getPitch();
                getCamera().mouseRotateTo(yaw, pitch);
                break;
            }
        }
    }
}
