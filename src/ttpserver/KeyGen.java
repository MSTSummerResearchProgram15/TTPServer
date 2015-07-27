package ttpserver;

//This class generates the required keys for a user.
public class KeyGen {
	private Params params;
	
	public KeyGen(Params params){
		this.params = params;
	}
	
	public User generate(){
		User user = new User();
		user.setSK(params.getzr().newRandomElement().getImmutable()); //private key
		user.setPK(params.getgpre().powZn(user.getSK()).getImmutable());
		user.setISK(user.getSK().invert().getImmutable()); //invert the secret key to calculate the proxy re-encryption key
                return user;
	}
	
	public void generateRK(int ownerID, int userID){
		//This must be updated to get data from database, not from User object
		user1.setReEncryptKey(user1.getPK().powZn(owner.getISK()).getImmutable()); 
	}
}