import java.util.Random;

public class Boss extends Monster implements HeroicUnit {
    private int abilityFrequency;
    private int combatTick;

    public Boss(char tile, int x, int y, String name, int healthPool, int attackPoints, int defensePoints, int experience, int visionRange, int abilityFrequency) {
        super(tile, x, y, name, healthPool, attackPoints, defensePoints, visionRange, experience);
        this.abilityFrequency = abilityFrequency;
        this.combatTick = 0;
    }

    @Override
    public void on_GameTick() {
        if (isPlayerInRange()) {
            if (combatTick == abilityFrequency) {
                resetCombatTick();
                castAbility();
            } else {
                incrementCombatTick();
                move();
            }
        } else {
            resetCombatTick();
            move();
        }
    }

    private boolean isPlayerInRange() {
        return range(player) < getVision_range();
    }

    private void resetCombatTick() {
        combatTick = 0;
    }

    private void incrementCombatTick() {
        combatTick++;
    }

    @Override
    public void castAbility() {
        int defense = rollDefense();
        int damage = calculateDamage(defense);
        sendAbilityMessage(defense, damage);
        if (damage > 0) {
            player.lose_health(damage);
        }
    }

    private int rollDefense() {
        Random random = new Random();
        return random.nextInt(player.getDefense_points());
    }

    private int calculateDamage(int defense) {
        return Math.max(0, getAttack_points() - defense);
    }

    private void sendAbilityMessage(int defense, int damage) {
        manager.sendMessage(player.getName() + " rolled " + defense + " defense points.");
        manager.sendMessage(getName() + " shot " + player.getName() + ", dealing " + damage + " ability damage.");
    }
}