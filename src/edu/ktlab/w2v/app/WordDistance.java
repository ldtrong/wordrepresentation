package edu.ktlab.w2v.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import edu.ktlab.w2v.distance.Distance;
import edu.ktlab.w2v.distance.DotProductDistance;

public class WordDistance extends App {
	public WordDistance(String path, int topNSize, Distance measure) {
		super(path, topNSize, measure);
	}

	public Set<WordEntry> distance(String word) {
		float[] wordVector = w2v.getWordVector(word);
		if (wordVector == null) {
			return null;
		}
		Set<Entry<String, float[]>> entrySet = w2v.getWordVectors().entrySet();
		float[] tempVector = null;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		String name = null;
		for (Entry<String, float[]> entry : entrySet) {
			name = entry.getKey();
			if (name.equals(word)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			dist = measure.distance(wordVector, tempVector);
			insertTopN(name, dist, tempVector, wordEntrys, topNSize);
		}
		return new TreeSet<WordEntry>(wordEntrys);
	}

	public static void main(String[] args) throws IOException {
		String word = "tình_yêu";
		int top = 40;
		WordDistance wd = new WordDistance("models/viwordreprs-v1.0.jbin", top,
				new DotProductDistance());
		wd.loadJW2VModel();
		System.out.println(wd.distance(word));
	}
}
