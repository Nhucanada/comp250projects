package assignment1;

public class SniperBee extends HoneyBee {

	private int attackDamage;
	private int piercingPower;
	public static int BASE_HEALTH;
	public static int BASE_COST;

	private static int count;
	
	public SniperBee(Tile position, int attackDamage, int piercingPower) {
		
		super(position, BASE_HEALTH, BASE_COST);
		this.attackDamage = attackDamage;
		this.piercingPower= piercingPower;
		
	}
	
	public boolean takeAction() {
		Tile pos = this.getPosition();
		
		if (pos.isOnThePath()) {
			if (pos.getNumOfHornets() == 0) {

				if (pos.isNest()) {
					return false;
				}
				else {
					pos.removeInsect(this);

					pos = pos.towardTheNest();

					pos.addInsect(this);
				
				}


				return false;
			}
			else {

				Hornet[] hornets = pos.getHornets();

				if (hornets == null){

					return false;
				}

				else if (pos.getNumOfHornets() <= piercingPower) {
					
					
					for(int i=0; i<hornets.length; i++) {

						hornets[i].takeDamage(attackDamage);
						
					}
					
				}
				else {
					
					for(int i=0; i<piercingPower; i++) {

						hornets[i].takeDamage(attackDamage);
						
					}
					
				}

				return true;
			}
		}
		else {

			return false;
			
		}

		
	}
	
}
