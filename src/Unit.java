import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Unit extends Tile {
    private String name;
    private Integer health_pool;
    private Integer current_health;
    private Integer attack_points;
    private Integer defense_points;
    protected static ArrayList<Enemy> enemyList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth_pool(Integer health_pool) {
        this.health_pool = health_pool;
    }

    public void setCurrent_health(Integer current_health) {
        this.current_health = current_health;
    }

    public void setAttack_points(Integer attack_points) {
        this.attack_points = attack_points;
    }

    public void setDefense_points(Integer defense_points) {
        this.defense_points = defense_points;
    }

    public Integer getHealth_pool() {
        return health_pool;
    }

    public Integer getCurrent_health() {
        return current_health;
    }

    public Integer getAttack_points() {
        return attack_points;
    }

    public Integer getDefense_points() {
        return defense_points;
    }

    public static List<Enemy> getEnemyList() {
        return enemyList;
    }

    public Unit(char tile, int x, int y, String name, Integer health_pool, Integer attack_points, Integer defense_points) {
        super(tile, x, y);
        this.name = name;
        this.health_pool = health_pool;
        this.current_health = health_pool;
        this.attack_points = attack_points;
        this.defense_points = defense_points;
        manager.setEnemies(enemyList);
    }

    public String getName() {
        return name;
    }

    public void addToEnemy(Enemy enemy) {
        enemyList.add(enemy);
    }

    public String description() {
        return getName() + ", Health: " + current_health +'/' + health_pool + ", Attack: " + attack_points + ", Defense: " + defense_points;
    }

    public int random_Defense(){
        Random random = new Random();
        int defense = random.nextInt(defense_points);
        manager.sendMessage(getName() + " rolled " + defense + " defense points");
        return defense;
    }

    public int random_Attack(){
        Random random = new Random();
        int attack = random.nextInt(attack_points);
        manager.sendMessage(getName() + " rolled " + attack + " defense points");
        return attack;
    }

    public void interact(Player player){
        player.interact(this);
    }

    public void interact(Enemy enemy){
        enemy.interact(this);
    }

    public void interact(Tile tile){
        tile.interact(this);
    }

    public void interact(Empty empty){
        this.swapTiles(empty);
    }

    public void interact(Wall wall){
        wall.interact(this);
    }

    public boolean isDead(){
        return current_health <= 0;
    }

    public List<Enemy> enemyList_byRange(int range){
        List<Enemy> enemyList1 = enemyList;
        List<Enemy> enemyList3 = new ArrayList<>();
        for (Enemy e: enemyList1) {
            if(e.range(this) < range) {
                enemyList3.add(e);
            }
        }
        return enemyList3;
    }
}
