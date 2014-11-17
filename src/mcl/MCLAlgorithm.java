package mcl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import utils.FileUtils;

public class MCLAlgorithm {

	private String fileName;
	private HashMap<String, ArrayList<String>> adjacencyList;
	private HashMap<String, Integer> indexMap;
	private double precision = 0.00000001;
	
	public MCLAlgorithm(String fileName){
		this.fileName = "/"+fileName;
		this.adjacencyList = new HashMap<String, ArrayList<String>>();
		this.indexMap = new HashMap<String, Integer>();
	}

	public void clustering(int r){

		double[][] adjacencyMatrix = null;
		adjacencyMatrix = buildAdjacencyMatrix();

		// normalize to canonical matrix
		double[][] transitionMatrix = null;
		transitionMatrix = normalize(adjacencyMatrix);

		double[][] expandedTranstionMatrix = null;
		int iterationNum = 0;
		while(true){
			++iterationNum;
			// expand
			expandedTranstionMatrix = expand(transitionMatrix);


			if(equals(transitionMatrix, expandedTranstionMatrix)) break;
			// inflate
			double[][] inflatedTransitionMatrix = null;
			inflatedTransitionMatrix = inflate(expandedTranstionMatrix, r);

			prune(inflatedTransitionMatrix);
			transitionMatrix = inflatedTransitionMatrix; 
		}
		System.out.println("number of iterations: "+iterationNum);
		findCluster(transitionMatrix);
	}

	public double[][] buildAdjacencyMatrix(){
		double[][] adjacencyMatrix = null;
		FileUtils fileUtils = new FileUtils();
		try {
			Scanner sc = fileUtils.readFileUsingScanner(fileName);
			int index = -1;
			String lastSeen = "";
			String key = "";
			String value = "";

			while(sc.hasNext()){
				String nextLine = sc.nextLine();
				String kv[] = null;
				if(nextLine!=null){
					kv = nextLine.split("\\s");
				}
				if(kv!=null && kv.length==2){
					key = kv[0];
					value = kv[1];
					if(!lastSeen.equals(key)){
						lastSeen = key;
					}
					if(!indexMap.containsKey(key)){
						index++;
						indexMap.put(key, index);
					}
					if(!indexMap.containsKey(value)){
						index++;
						indexMap.put(value, index);
					}
				}
				ArrayList<String> list = null;

				if(adjacencyList.containsKey(key)){
					list = adjacencyList.get(key);
					list.add(value);
				}else{
					list = new ArrayList<String>();
					list.add(value);
					adjacencyList.put(key,list);
				}
				if(adjacencyList.containsKey(value)){
					list = adjacencyList.get(value);
					list.add(key);
				}else{
					list = new ArrayList<String>();
					list.add(key);
					adjacencyList.put(value,list);
				}
			}

			adjacencyMatrix = new double[adjacencyList.size()][adjacencyList.size()];

			//init the adjacencyMatrix to all zeroes
			for(int i=0; i<adjacencyList.size(); i++)
				for(int j=0; j<adjacencyList.size(); j++){
					if(i==j)
						adjacencyMatrix[i][j] = 1;
					else
						adjacencyMatrix[i][j] = 0;
				}


			// fill the adjacencyMatrix using the data in the adjacencyList
			System.out.println("size of input: "+adjacencyList.size());
			Iterator iter = adjacencyList.entrySet().iterator();

			while(iter.hasNext()){
				Entry entry = (Entry)iter.next();
				key = (String)entry.getKey();
				ArrayList<String> list = (ArrayList<String>)entry.getValue();
				for(String value1 : list){
					adjacencyMatrix[indexMap.get(key)][indexMap.get(value1)] = 1;
					adjacencyMatrix[indexMap.get(value1)][indexMap.get(key)] = 1;
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return adjacencyMatrix;
	}

	public double[][] normalize(double[][] adjacencyMatrix){
		double[][] transitionMatrix = new double[adjacencyMatrix.length][adjacencyMatrix.length];

		for(int i=0; i<adjacencyList.size(); i++)
			for(int j=0; j<adjacencyList.size(); j++)
				transitionMatrix[i][j] = 0;

		double[] colSum = new double[adjacencyMatrix.length];
		for(int i=0; i<adjacencyMatrix.length; i++){
			colSum[i] = 0;
			for(int j=0; j<adjacencyMatrix.length; j++){
				if(adjacencyMatrix[j][i]!=0){
					colSum[i]+=adjacencyMatrix[j][i];
				}
			}
		}

		for(int i=0; i<adjacencyMatrix.length; i++){
			for(int j=0; j<adjacencyMatrix.length; j++){
				if(colSum[i]!=0)
					transitionMatrix[j][i] = adjacencyMatrix[j][i]/colSum[i];
			}
		}
		return transitionMatrix;
	}

	public double[][] expand(double[][] transitionMatrix){
		double[][] expandedTransitionMatrix = new double[transitionMatrix.length][transitionMatrix.length];
		for(int i=0; i<transitionMatrix.length; i++){
			for(int j=0; j<transitionMatrix.length; j++){
				for(int k=0; k<transitionMatrix.length; k++){
					expandedTransitionMatrix[i][j] += transitionMatrix[i][k] * transitionMatrix[k][j];
				}
			}
		}
		return expandedTransitionMatrix;
	}

	public double[][] inflate(double[][] expandedTransitionMatrix, int r){
		for(int i=0; i<expandedTransitionMatrix.length; i++){
			for(int j=0; j<expandedTransitionMatrix.length; j++){
				expandedTransitionMatrix[i][j] = Math.pow(expandedTransitionMatrix[i][j], r);
			}
		}
		return normalize(expandedTransitionMatrix);
	}

	public void prune(double[][] x){
		for(int i=0; i<x.length; i++){
			for(int j=0; j<x.length; j++){
				if(x[i][j] < precision){
					x[i][j] = 0.0;
				}
			}
		}
	}

	public boolean equals(double[][] a, double[][] b){
		if(a==null || b==null) return false;
		if(a.length!=b.length) return false;
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a.length; j++){
				if(a[i][j] - b[i][j] > precision){
					return false;
				}
			}
		}
		return true;
	}

	public void printMatrix(double[][] a){
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a.length; j++){
				System.out.println(a[i][j]+" ");
			}
			System.out.println();
		}
	}

	public void printFirstRows(double[][] a, int x){
		for(int i=0; i<x; i++){
			for(int j=0; j<a.length; j++){
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
	}

	public void findCluster(double[][] a){
		HashMap<String, ArrayList<String>> clusterMap = new HashMap<String, ArrayList<String>>();

		int clusterNum = 0 ;
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a.length; j++){
				if(a[i][j]>0){
					String key = i+"";
					String value = j+"";
					ArrayList<String> list;
					if(clusterMap.containsKey(key)){
						list = clusterMap.get(key);
						list.add(value);
					}else{
						list = new ArrayList<String>();
						list.add(value);
						clusterMap.put(key, list);
						clusterNum++;
					}
				}
			}
		}
		System.out.println("num of clusters: "+clusterNum);
	}
}
