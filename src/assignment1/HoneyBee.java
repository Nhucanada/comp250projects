package assignment1;

public abstract class HoneyBee extends Insect {
	
	int foodCost;
	public static double HIVE_DMG_REDUCTION;
	
	public HoneyBee (Tile position, int hp, int foodCost) {
		
		super(position, hp);
		this.foodCost = foodCost;
		
	}
	
	public int getCost() {
		
		return this.foodCost;
		
	}
	
	public void takeDamage(int damage) {

		int hp = this.getHealth();
		Tile position = this.getPosition();
		
		if(position.isHive()) {
			double finalDMG = (damage - HIVE_DMG_REDUCTION)-((damage - HIVE_DMG_REDUCTION)%1);

			hp -= ((int)finalDMG);
		}
		else {
			hp -= damage;
		}
		
		if (hp <= 0) {
			boolean removed;
			removed = position.removeInsect(this);
			
			if (removed == false) {
				String errMsg = "Remove failed";
				throw new IllegalArgumentException(errMsg);
			}
			else {
				;
			}
		}
		else {
			;
		}

		this.setHealth(hp);

	}
	
}
