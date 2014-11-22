package mcl;

public class MCLDriver {

	public static void main(String[] args) {
		MCLAlgorithm mcl = null;
		
		// power is constant = 2
		
		double r = 0.0;
		
		// r = 1.36;
//		mcl = new MCLAlgorithm("attweb_net.txt");
		
		 r = 1.21;
//		 r = 1.25;
//		 r = 1.26;
		mcl= new MCLAlgorithm("physics_collaboration_net.txt");
		
		// r = 1.19;
//		mcl = new MCLAlgorithm("yeast_undirected_metabolic.txt");
		
//		for(int i=0; i<10;i++){
//			r = 1.15+0.01*i;
			System.out.println("r: "+r);
			mcl.clustering(r);	
//		}
	}
}
