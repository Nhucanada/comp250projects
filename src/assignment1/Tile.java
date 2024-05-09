package assignment1;

public class Tile {

    private int foodPresent;
    private boolean beehiveBuilt;
    private boolean hornetNestBuilt;
    private boolean onPath;
    private Tile nextTileBeehive;
    private Tile nextTileHornetNest;
    private HoneyBee bee;
    private SwarmOfHornets hornets;
    private boolean onFire;

    public Tile() {

        this.foodPresent = 0;
        this.beehiveBuilt = false;
        this.hornetNestBuilt = false;
        this.onPath = false;
        this.bee = null;
        this.hornets = new SwarmOfHornets();
        this.onFire = false;

    }

    public Tile(int foodPresent, boolean beehiveBuilt, boolean hornetNestBuilt, boolean onPath, Tile nextTileBeehive, Tile nextTileHornetNest, HoneyBee bee, SwarmOfHornets hornets) {

        this.foodPresent = foodPresent;
        this.beehiveBuilt = beehiveBuilt;
        this.hornetNestBuilt = hornetNestBuilt;
        this.onPath = onPath;
        this.bee = bee;
        this.hornets = hornets;
        this.onFire = false;
        this.nextTileBeehive = nextTileBeehive;
        this.nextTileHornetNest = nextTileHornetNest;

    }

    public boolean isHive() {

        return this.beehiveBuilt;

    }

    public boolean isNest() {
        return this.hornetNestBuilt;
    }

    public void buildHive() {

        this.beehiveBuilt = true;

    }

    public void buildNest() {
        this.hornetNestBuilt = true;
    }

    public boolean isOnThePath() {
        return this.onPath;
    }

    public Tile towardTheHive() {

        if (this.onPath == true) {
            if (this.beehiveBuilt == false) {

                return this.nextTileBeehive;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }

    }

    public Tile towardTheNest() {

        if (this.onPath == true) {
            if (this.hornetNestBuilt == false) {
                return this.nextTileHornetNest;
            }
            else {

                return null;
            }
        }
        else {
            return null;
        }

    }

    public void createPath(Tile nextHive, Tile nextNest) {

        String errMsg = "Invalid Input";

        if (nextHive == null) {

            if (this.beehiveBuilt == true) {

                ;

            }
            else {

                throw new IllegalArgumentException(errMsg);

            }

        }
        else if (nextNest == null) {

            if (this.hornetNestBuilt == true) {

                ;

            }
            else {

                throw new IllegalArgumentException(errMsg);

            }

        }
        else {
            ;
        }
        this.nextTileBeehive = nextHive;
        this.nextTileHornetNest = nextNest;
        this.onPath = true;

    }

    public int collectFood() {

        int foodHolder = this.foodPresent;
        this.foodPresent = 0;
        return foodHolder;

    }

    public void storeFood(int food) {

        this.foodPresent += food;

    }

    public int getNumOfHornets() {

        return this.hornets.sizeOfSwarm();

    }

    public HoneyBee getBee() {

        return this.bee;

    }

    public Hornet getHornet() {

        return this.hornets.getFirstHornet();

    }

    public Hornet[] getHornets() {

        return this.hornets.getHornets();

    }

    public boolean addInsect (Object insect) {

        if (insect instanceof HoneyBee) {
            if (this.bee == null) {
                if (this.isNest() == false) {
                    this.bee = (HoneyBee) insect;
                    ((Insect)insect).setPosition(this);
                    return true;
                }
                else {

                    return false;
                }

            }
            else {

                return false;
            }
        }
        else if (insect instanceof Hornet) {
            if (this.isOnThePath()) {

                this.hornets.addHornet((Hornet)insect);
                ((Insect)insect).setPosition(this);
                return true;
            }
            else {

                return false;
            }
        }
        else {

            return false;
        }
    }


    public boolean removeInsect (Object insect) {
        if(insect instanceof HoneyBee) {
            this.bee = null;
            ((Insect)insect).setPosition(null);
            return true;
        }
        else if (insect instanceof Hornet) {
            boolean success = this.hornets.removeHornet((Hornet) insect);
            if (success == true) {
                ((Insect)insect).setPosition(null);
            }
            else {
                ;
            }
            return success;
        }
        else {
            return false;
        }

    }

    public void setOnFire() {

        this.onFire = true;

    }

    public boolean isOnFire() {

        return this.onFire;

    }
}