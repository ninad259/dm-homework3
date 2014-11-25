package mcl;

public class MCLDriver {

	public static void main(String[] args) {
		MCLAlgorithm mcl = null;
		
		// power is constant = 2
		
		double r = 0.0;
		
		// e=2
//		 r = 1.36;
		 // e = 3
//		 r = 1.70;
//		mcl = new MCLAlgorithm("attweb_net.txt");

		// e=2
//		 r = 1.25;
//		e=3
//		r = 1.751;
//		mcl= new MCLAlgorithm("physics_collaboration_net.txt");
		
//		e = 2
//		 r = 1.204;
//		e=3
//		r = 
		mcl = new MCLAlgorithm("yeast_undirected_metabolic.txt");
		
		for(int i=0; i<20;i++){
			r = 1.5+0.01*i;
			System.out.println("r: "+r);
			mcl.clustering(r);	
		}
	}
}
