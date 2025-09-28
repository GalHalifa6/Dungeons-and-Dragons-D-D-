public abstract class Player extends Unit implements HeroicUnit {
    private Integer experience;
    private Integer level;

    // Constructor
    public Player(int x, int y, String name, Integer health_pool, Integer attack_points, Integer defense_points) {
        super('@', x, y, name, health_pool, attack_points, defense_points);
        this.experience = 0;
        this.level = 1;
        Enemy.player = this;
        manager.setPlayer(this);
    }

    // Getters
    public Integer getExperience() {
        return experience;
    }

    public Integer getLevel() {
        return level;
    }

    // Level up logic
    public void levelUp() {
        experience -= level * 50;
        level++;
        setHealth_pool(getHealth_pool() + (10 * level));
        setCurrent_health(getHealth_pool());
        setAttack_points(getAttack_points() + (4 * level));
        setDefense_points(getDefense_points() + level);
    }

    // Add experience and handle level up
    public void addExp(int experience) {
        this.experience += experience;
        while (this.experience >= (level * 50)) {
            levelUp();
        }
    }

    // Abstract method for special ability
    public abstract void castAbility();

    // Description of the player
    public String description() {
        return super.description() + ", Level: " + level + ", Experience: " + experience + '/' + (50 * level);
    }

    // Abstract method for game tick
    public abstract void on_GameTick();

    // Battle logic
    protected void battle(Enemy enemy) {
        manager.sendMessage(getName() + " engaged in combat with " + enemy.getName() + '.');
        int rand_att = random_Attack();
        int rand_def = enemy.random_Defense();
        int damage = Math.max(rand_att - rand_def, 0);
        manager.sendMessage(getName() + " dealt " + damage + " damage to " + enemy.getName() + ".");
        enemy.loseHealth(damage);
        manager.sendMessage(enemy.description());
    }

    // Handle health loss
    public void lose_health(int damage) {
        setCurrent_health(getCurrent_health() - damage);
        if (isDead()) {
            setTile('X');
            manager.sendMessage("Game is over, you lost :(");
            manager.is_GameOver();
        }
    }

    // Interaction with an enemy
    public void interact(Enemy enemy) {
        this.battle(enemy);
    }

    // Interaction with another player (empty implementation)
    public void interact(Player player) {}

    // Movement methods
    public void moveUp() {
        moveToTile(getX() - 1, getY());
    }

    public void moveDown() {
        moveToTile(getX() + 1, getY());
    }

    public void moveLeft() {
        moveToTile(getX(), getY() - 1);
    }

    public void moveRight() {
        moveToTile(getX(), getY() + 1);
    }

    // Helper method for movement
    private void moveToTile(int x, int y) {
        Visited tile = board.getTile(x, y);
        tile.accept(this);
    }
}