package assignment1;

public class Hornet extends Insect {

	private int attackDamage;
	public static int BASE_FIRE_DMG;
	private boolean isQueen;
	private static int queenCount = 0;

	public Hornet(Tile position, int hp, int attackDamage) {

		super(position, hp);
		this.attackDamage = attackDamage;
		this.isQueen = false;

	}

	public boolean takeAction() {

		if(this.isTheQueen()){
			return takeActionQueen();
		}

		Tile position = this.getPosition();

		if (position.isOnFire()) {
			this.takeDamage(BASE_FIRE_DMG);
		}
		if (position.getBee() != null) {

			((Insect) position.getBee()).takeDamage(this.attackDamage);
			return true;

		}
		else {

			if (position.isHive()) {
				return false;
			} else {
				position.removeInsect(this);
				position = position.towardTheHive();
				position.addInsect(this);
				return true;

			}

		}

	}

	public boolean takeActionQueen() {
		Tile position = this.getPosition();

		if (position.isOnFire()) {
			this.takeDamage(BASE_FIRE_DMG);
		}
		if (position.getBee() != null) {

			((Insect) position.getBee()).takeDamage(this.attackDamage);


		} else {

			if (position.isHive()) {
				return false;
			} else {
				position.removeInsect(this);
				position = position.towardTheHive();
				position.addInsect(this);

			}

		}
		if (this.getHealth() > 0) {
			if (position.isOnFire()) {
				this.takeDamage(BASE_FIRE_DMG);
			}
			if (position.getBee() != null) {

				((Insect) position.getBee()).takeDamage(this.attackDamage);
				return true;

			} else {

				if (position.isHive()) {
					return false;
				} else {
					position.removeInsect(this);
					position = position.towardTheHive();
					if(position != null) {
						position.addInsect(this);
					}
					return true;
				}

			}
		}
		return false;
	}


		public boolean equals (Insect object){

			if (object instanceof Hornet) {
				if (object.getPosition() == this.getPosition()) {
					if (object.getHealth() == this.getHealth()) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}

		}


		public boolean isTheQueen() {

			return this.isQueen;

		}

		public void promote() {

			if (queenCount == 0) {

				this.isQueen = true;

			} else {

				;

			}

		}


}
	
