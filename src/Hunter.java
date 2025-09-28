import java.util.List;

public class Hunter extends Player {

    private int range;
    private int arrowCount;
    private int tickCount;

    public Hunter(int x, int y, String name, int healthPool, int attackPoints, int defensePoints, int range) {
        super(x, y, name, healthPool, attackPoints, defensePoints);
        this.range = range;
        this.arrowCount = 10;
        this.tickCount = 0;
    }

    @Override
    public void castAbility() {
        if (arrowCount > 0) {
            arrowCount--;
            Enemy target = findClosestEnemyInRange();
            if (target != null) {
                attackEnemy(target);
            } else {
                manager.sendMessage(getName() + " tried to shoot, but no enemies were in range.");
            }
        } else {
            manager.sendMessage(getName() + " tried to shoot, but there were not enough arrows.");
        }
    }

    private Enemy findClosestEnemyInRange() {
        List<Enemy> enemiesInRange = enemyList_byRange(range);
        if (enemiesInRange.isEmpty()) {
            return null;
        }
        return enemiesInRange.stream()
                .min((e1, e2) -> Double.compare(range(e1), range(e2)))
                .orElse(null);
    }

    private void attackEnemy(Enemy enemy) {
        manager.sendMessage(getName() + " shot " + enemy.getName());
        int defense = enemy.random_Defense();
        int damage = Math.max(getAttack_points() - defense, 0);
        manager.sendMessage(getName() + " hit " + enemy.getName() + " for " + damage + " ability damage.");
        enemy.loseHealth(damage);
    }

    @Override
    public void on_GameTick() {
        tickCount++;
        if (tickCount == 10) {
            arrowCount += getLevel();
            tickCount = 0;
        }
    }

    @Override
    public void interact(Unit unit) {
        unit.interact(this);
    }

    @Override
    public void levelUp() {
        super.levelUp();
        arrowCount += 10 * getLevel();
        setAttack_points(getAttack_points() + 2 * getLevel());
        setDefense_points(getDefense_points() + getLevel());
    }

    @Override
    public String description() {
        return super.description() + " Arrows: " + arrowCount;
    }
}