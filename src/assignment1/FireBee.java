package assignment1;

public class FireBee extends HoneyBee {

	private int maxAttackRange;
	public static int BASE_HEALTH;
	public static int BASE_COST;
	
	public FireBee (Tile position, int maxAttackRange) {
		
		super(position, BASE_HEALTH, BASE_COST);
		this.maxAttackRange = maxAttackRange;
		
	}
	
	public boolean takeAction() { // needs a lot of work

		Tile pos = this.getPosition();
		if (pos.isOnThePath()) {

			for (int i = 0; i < (maxAttackRange); i++) {
				if (pos.isNest()) {
					return false;
				}
				else {
					pos.removeInsect(this);
					pos = pos.towardTheNest();
					pos.addInsect(this);

				}
				if (pos.isOnFire() == false && pos.getNumOfHornets() > 0) {
					pos.setOnFire();
					return true;

				} else {
					;
				}

			}
			return false;
		}
		else {

			return false;
			
		}
		}

		
	}
	

