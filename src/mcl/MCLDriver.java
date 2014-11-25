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
		
		/*double precision = 0.00000001;
		for(int i=1; i<8; i++){
			System.out.println("r: "+r+" , precision: "+precision*Math.pow(10, i));
			mcl = new MCLAlgorithm("attweb_net.txt", precision*Math.pow(10, i));
			mcl.clustering(r);	
		}*/
		
		// e=2
//		 r = 1.25;
//		e=3
//		r = 1.751;
//		mcl= new MCLAlgorithm("physics_collaboration_net.txt");
		
//		e = 2
		 r = 1.204;
//		e=3
//		r = 1.488;
		mcl = new MCLAlgorithm("yeast_undirected_metabolic.txt");
		
//		for(int i=0; i<30;i=i+4){
//			r = 1.36+0.01*i;
			System.out.println("r: "+r);
			mcl.clustering(r);	
//		}
	}
}
