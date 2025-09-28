import java.util.List;
import java.util.Random;

/**
 * Mage class represents a player with magical abilities.
 * It uses mana to cast spells and can hit multiple enemies within range.
 */
public class Mage extends Player {
    private Integer manaPool;
    private Integer currentMana;
    private Integer manaCost;
    private Integer spellPower;
    private Integer hitsCount;
    private Integer abilityRange;

    public Mage(int x, int y, String name, Integer healthPool, Integer attackPoints, Integer defensePoints,
                Integer manaPool, Integer manaCost, Integer spellPower, Integer hitsCount, Integer abilityRange) {
        super(x, y, name, healthPool, attackPoints, defensePoints);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public void castAbility() {
        if (currentMana >= manaCost) {
            currentMana -= manaCost;
            int hits = 0;
            while (hits < hitsCount) {
                Enemy target = getRandomEnemyInRange();
                if (target != null) {
                    attackEnemy(target);
                    hits++;
                } else {
                    break;
                }
            }
        } else {
            manager.sendMessage(String.format("%s tried to cast Blizzard, but there was not enough mana: %d/%d",
                    getName(), currentMana, manaCost));
        }
    }

    private Enemy getRandomEnemyInRange() {
        List<Enemy> enemiesInRange = enemyList_byRange(abilityRange);
        if (enemiesInRange.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return enemiesInRange.get(random.nextInt(enemiesInRange.size()));
    }

    private void attackEnemy(Enemy enemy) {
        int defense = enemy.random_Defense();
        int damage = Math.max(spellPower - defense, 0);
        manager.sendMessage(String.format("%s hit %s for %d ability damage.", getName(), enemy.getName(), damage));
        enemy.loseHealth(damage);
    }

    @Override
    public void on_GameTick() {
        currentMana = Math.min(manaPool, currentMana + getLevel());
    }

    @Override
    public void levelUp() {
        super.levelUp();
        manaPool += 25 * getLevel();
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        spellPower += 10 * getLevel();
    }

    @Override
    public void interact(Unit unit) {
        unit.interact(this);
    }

    @Override
    public String description() {
        return String.format("%s, Mana: %d/%d, Spell power: %d",
                super.description(), currentMana, manaPool, spellPower);
    }
}