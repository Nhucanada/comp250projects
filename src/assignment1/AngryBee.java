package assignment1;

public class AngryBee extends HoneyBee{

	private int attackDamage;
	public static int BASE_HEALTH;
	public static int BASE_COST;
	
	public AngryBee (Tile position, int attackDamage) {
		
		super(position, BASE_HEALTH, BASE_COST); // fix
		this.attackDamage = attackDamage;
		
		
	}
	
	public boolean takeAction() { // serious fucked up
		
		Tile pos = this.getPosition();
		boolean hasMoved = false;
		if (pos.isOnThePath() && pos.isNest() == false) {

			if(pos.getNumOfHornets() == 0) {
				pos.removeInsect(this);
				pos = pos.towardTheNest();
				pos.addInsect(this);
				hasMoved = true;
				if (pos.getNumOfHornets() == 0) {
					pos.removeInsect(this);
					pos = pos.towardTheHive();
					pos.addInsect(this);
					return false;

				}
			}
			Hornet[] hornets = pos.getHornets();
			((Insect)hornets[0]).takeDamage(attackDamage);
			if (hasMoved){
				pos.removeInsect(this);
				pos = pos.towardTheHive();
				pos.addInsect(this);
			}
			return true;

		}
		

		return false;
	}
}
	

