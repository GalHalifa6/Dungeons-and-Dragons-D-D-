public abstract class Enemy extends Unit {
    private final Integer experienceValue;
    protected static Player player;

    /**
     * Constructor for Enemy.
     *
     * @param tile            The character representing the enemy on the board.
     * @param x               The x-coordinate of the enemy.
     * @param y               The y-coordinate of the enemy.
     * @param name            The name of the enemy.
     * @param healthPool      The maximum health of the enemy.
     * @param attackPoints    The attack points of the enemy.
     * @param defensePoints   The defense points of the enemy.
     * @param experienceValue The experience value the player gains upon defeating the enemy.
     */
    public Enemy(char tile, int x, int y, String name, Integer healthPool, Integer attackPoints, Integer defensePoints, Integer experienceValue) {
        super(tile, x, y, name, healthPool, attackPoints, defensePoints);
        this.experienceValue = experienceValue;
        addToEnemy(this);
    }

    /**
     * Abstract method to define behavior on each game tick.
     */

    public abstract void on_GameTick();

    /**
     * Handles interaction with a player.
     *
     * @param player The player interacting with the enemy.
     */
    public void interact(Player player) {
        manager.sendMessage(getName() + " engaged in combat with " + player.getName() + '.');
        int damageDealt = calculateDamage(player);
        applyDamageToPlayer(player, damageDealt);
        manager.sendMessage(description());
    }

    /**
     * Handles interaction with another enemy (no action by default).
     *
     * @param enemy The enemy interacting with this enemy.
     */
    public void interact(Enemy enemy) {
        // No interaction by default
    }

    /**
     * Reduces the health of the enemy and handles death logic.
     *
     * @param damage The amount of damage to apply.
     */
    public void loseHealth(int damage) {
        setCurrent_health(getCurrent_health() - damage);
        if (isDead()) {
            handleDeath();
        }
    }


    /**
     * Calculates the damage dealt to the player.
     *
     * @param player The player being attacked.
     * @return The amount of damage dealt.
     */
    private int calculateDamage(Player player) {
        int attackRoll = random_Attack();
        int defenseRoll = player.random_Defense();
        return Math.max(attackRoll - defenseRoll, 0);
    }

    /**
     * Applies damage to the player and sends appropriate messages.
     *
     * @param player      The player being attacked.
     * @param damageDealt The amount of damage dealt.
     */
    private void applyDamageToPlayer(Player player, int damageDealt) {
        if (damageDealt > 0) {
            manager.sendMessage(getName() + " dealt " + damageDealt + " damage to " + player.getName() + ".");
            player.lose_health(damageDealt);
        } else {
            manager.sendMessage(getName() + " dealt no damage to " + player.getName() + ".");
        }
    }

    /**
     * Handles the death of the enemy, including experience gain for the player.
     */
    private void handleDeath() {
        manager.sendMessage(getName() + " died. " + player.getName() + " gained " + experienceValue + " experience.");
        player.addExp(experienceValue);
        manager.removeEnemy(this);
    }
}