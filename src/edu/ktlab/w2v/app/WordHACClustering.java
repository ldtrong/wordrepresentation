package edu.ktlab.w2v.app;

import java.io.IOException;
import java.util.Set;

import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.CompleteLinkageStrategy;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;

import edu.ktlab.w2v.distance.CosineDistance;
import edu.ktlab.w2v.distance.Distance;
import edu.ktlab.w2v.distance.DotProductDistance;

public class WordHACClustering extends App {
	WordDistance wd;
	ClusteringAlgorithm alg;

	public WordHACClustering(String path, int topNSize, Distance measure) throws IOException {
		super(path, topNSize, measure);
		wd = new WordDistance(path, topNSize, new DotProductDistance());
		wd.loadJW2VModel();
		alg = new DefaultClusteringAlgorithm();
	}

	public Cluster clustering(String word) {
		double[][] DISTANCES = new double[topNSize][topNSize];
		String[] NAMES = new String[topNSize];
		Set<WordEntry> entries = wd.distance(word);
		if( entries == null || entries.size() == 0) return null;
		WordEntry[] instances = entries.toArray(new WordEntry[entries.size()]);
		for (int i = 0; i < instances.length; i++) {
			for (int j = 0; j < instances.length; j++) {
				if (instances[i].equals(instances[j]))
					DISTANCES[i][j] = 0;
				DISTANCES[i][j] = measure.distance(instances[i].vector, instances[j].vector);
			}
		}

		for (int i = 0; i < instances.length; i++)
			NAMES[i] = instances[i].name;

		Cluster cluster = alg.performClustering(DISTANCES, NAMES, new CompleteLinkageStrategy());

		return cluster;
	}
	
	public static void main(String[] args) throws IOException {
		String word = "hạnh_phúc";
		int top = 100;
		WordHACClustering wd = new WordHACClustering("models/viwordreprs-236k.jbin", top, new CosineDistance());
		Cluster cluster = wd.clustering(word);
		cluster.toConsole(0);
	}
}
