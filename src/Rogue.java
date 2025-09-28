import java.util.List;

public class Rogue extends Player {
    private static final int MAX_ENERGY = 100;
    private static final int ENERGY_REGEN = 10;
    private static final int ABILITY_RANGE = 2;

    private int cost;
    private int currentEnergy;

    // Constructor
    public Rogue(int x, int y, String name, int healthPool, int attackPoints, int defensePoints, int cost) {
        super(x, y, name, healthPool, attackPoints, defensePoints);
        this.cost = cost;
        this.currentEnergy = MAX_ENERGY;
    }

    // Cast special ability
    @Override
    public void castAbility() {
        if (currentEnergy >= cost) {
            manager.sendMessage(getName() + " used Fan of Knives");
            currentEnergy -= cost;
            List<Enemy> enemiesInRange = enemyList_byRange(ABILITY_RANGE);
            for (Enemy enemy : enemiesInRange) {
                int defense = enemy.random_Defense();
                int damage = Math.max(getAttack_points() - defense, 0);
                manager.sendMessage(getName() + " attacked " + enemy.getName() + " for " + damage + " ability damage.");
                enemy.loseHealth(damage);
            }
        } else {
            manager.sendMessage(getName() + " tried to cast Fan of Knives, but not enough energy: " + currentEnergy + "/" + cost);
        }
    }

    // Handle game tick
    @Override
    public void on_GameTick() {
        currentEnergy = Math.min(currentEnergy + ENERGY_REGEN, MAX_ENERGY);
    }

    // Level up logic
    @Override
    public void levelUp() {
        super.levelUp();
        currentEnergy = MAX_ENERGY;
        setAttack_points(getAttack_points() + (3 * getLevel()));
    }

    // Interaction with other units
    @Override
    public void interact(Unit unit) {
        unit.interact(this);
    }

    // Description of the Rogue
    @Override
    public String description() {
        return super.description() + ", Energy: " + currentEnergy + "/" + MAX_ENERGY;
    }
}