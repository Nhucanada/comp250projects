package assignment1;

public class SwarmOfHornets {
	private Hornet[] Hornets;
	int swarmSize;
	public static double QUEEN_BOOST;
	
	public SwarmOfHornets () {
		
		this.swarmSize = 0;
		this.Hornets = new Hornet[swarmSize];
		
	}
	
	public int sizeOfSwarm() {
		
		return swarmSize;
		
	}
	
	public Hornet[] getHornets() {
		
		return Hornets;
		
	}
	
	public Hornet getFirstHornet () {
		if(Hornets.length !=0){
			return Hornets[0];
		}
		else{
			return null;
		}
		
	}
	
	public void addHornet(Hornet hornet) {
		boolean isClone = false;
		for(int i=0; i<this.Hornets.length; i++){

			if(Hornets[i] == hornet){
				isClone = true;
			}

		}

		if(!isClone){
			this.swarmSize += 1;
			Hornet[] newHornets = new Hornet[swarmSize];

			for(int i=0; i<this.Hornets.length; i++) {

				newHornets[i] = this.Hornets[i];

			}

			newHornets[Hornets.length] = hornet;

			this.Hornets = newHornets;

			if (hornet.isTheQueen()) {
				for(int i=0; i<this.Hornets.length; i++) {
					if(!Hornets[i].isTheQueen()){
						((Insect)Hornets[i]).regenerateHealth(QUEEN_BOOST); //fix
					}
				}

			}
		}

		
	}
	
	public boolean removeHornet(Hornet hornet) {
		
		int index;
		
		for(int i=0; i<this.Hornets.length; i++) {
			if (Hornets[i] == hornet) {
				
				index = i;
				
				this.swarmSize -= 1;
				Hornet[] newHornets = new Hornet[swarmSize];
				
				for(int z=0; z<this.Hornets.length; z++) {
					
					if(z < index) {
						newHornets[z] = this.Hornets[z];
					}
					else if (z > index) {
						newHornets[z-1] = this.Hornets[z];
					}
					else {
						;
					}
				}
				
				this.Hornets = newHornets;
				
				return true;
			}
			else {
				;
			}
		}
		
		return false;
		
	}
	
}
