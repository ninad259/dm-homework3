package mcl;

public class MCLDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MCLAlgorithm mcl = null;
		mcl = new MCLAlgorithm("attweb_net.txt");
//		mcl= new MCLAlgorithm("physics_collaboration_net.txt");
//		mcl = new MCLAlgorithm("yeast_undirected_metabolic.txt");
		
		for(int r=2; r<5; r++){
			System.out.println("r: "+r);
			mcl.clustering(r);
		}
			
	}
}
