import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private Integer remaining_cooldown;
    private Integer ability_cooldown;

    public Warrior(int x, int y, String name, Integer health_pool, Integer attack_points, Integer defense_points, Integer ability_cooldown) {
        super(x, y, name, health_pool, attack_points, defense_points);
        this.ability_cooldown = ability_cooldown;
        this.remaining_cooldown = 0;
    }

    public void castAbility() {
        if (remaining_cooldown == 0) {
            remaining_cooldown = ability_cooldown;
            setCurrent_health(Math.min(getCurrent_health() + (10 * getDefense_points()), getHealth_pool()));
            manager.sendMessage(getName() + " used Avenger's Shield");
            List<Enemy> enemies_list = getEnemyList();
            if (!enemies_list.isEmpty()) {
                List<Enemy> enemies_inRange = enemyList_byRange(3);
                if (!enemies_inRange.isEmpty()) {
                    Random random = new Random();
                    Enemy random_enemy = enemies_inRange.get(random.nextInt(enemies_inRange.size()));
                    int defense = random_enemy.random_Defense();
                    manager.sendMessage(getName() + " hit " + random_enemy.getName() + " for " + ((getHealth_pool() / 10) - defense) + " ability damage");
                    random_enemy.loseHealth((getHealth_pool() / 10) - defense);
                }
            }
        } else {
            manager.sendMessage(getName() + " tried to cast Avenger's Shield, but there is a cooldown: " + remaining_cooldown);
        }
    }

    public void levelUp() {
        super.levelUp();
        remaining_cooldown = 0;
        setHealth_pool(getHealth_pool() + (5 * getLevel()));
        setAttack_points(getAttack_points() + (2 * getLevel()));
        setDefense_points(getDefense_points() + getLevel());
    }

    public void on_GameTick() {
        remaining_cooldown = Math.max(remaining_cooldown - 1, 0);
    }

    public void interact(Unit unit) {
        unit.interact(this);
    }

    public String description() {
        return super.description() + ", Cooldown: " + remaining_cooldown + '/' + ability_cooldown;
    }
}