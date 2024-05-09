package assignment1;

public abstract class Insect {
	
	private Tile position;
	private int hp;
	
	public Insect (Tile position, int hp) {
		
		String errMsg = "Add failed";
		
		this.position = position;
		this.hp = hp;
		
		boolean added;
		added = position.addInsect(this);
		
		if (added == false) {
			throw new IllegalArgumentException(errMsg);
		}
		else {
			;
		}
		
	}
	
	public final Tile getPosition () {
		
		return position;
		
	}
	
	public final int getHealth () {
		
		return hp;
		
	}

	public final void setHealth (int health){

		this.hp = health;

	}
	
	public final void setPosition (Tile newTile) {
		
		this.position = newTile;
		
	}
	
	public void takeDamage(int damage) {
		
		this.hp -= damage;
		if (this.hp <= 0) {
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
		
	}
	
	public abstract boolean takeAction();
	
	public boolean equals(Insect object) {
		
		if (object instanceof Insect) {
			if(object.position ==  this.position) {
				if (object.hp == this.hp) {
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
		else {
			return false;
		}
		
	}
	
	public void regenerateHealth(double regen) {
		
		double health = (double) this.hp;
		regen += 1;
		health *= regen;

		double newHealth = health - (health % 1);
		
		int intHealth = (int) newHealth;
		this.hp = intHealth;
		
	}
	
}
