package edu.ktlab.w2v.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

public class WordAnalogy extends App {
	public WordAnalogy(String path, int topNSize) {
		super(path, topNSize);
	}

	public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
		float[] wv0 = w2v.getWordVector(word0);
		float[] wv1 = w2v.getWordVector(word1);
		float[] wv2 = w2v.getWordVector(word2);

		if (wv1 == null || wv2 == null || wv0 == null) {
			return null;
		}
		float[] wordVector = new float[w2v.getSize()];
		for (int i = 0; i < w2v.getSize(); i++) {
			wordVector[i] = wv1[i] - wv0[i] + wv2[i];
		}
		float[] tempVector;
		String name;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		for (Entry<String, float[]> entry : w2v.getWordVectors().entrySet()) {
			name = entry.getKey();
			if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			for (int i = 0; i < wordVector.length; i++) {
				dist += wordVector[i] * tempVector[i];
			}
			insertTopN(name, dist, wordEntrys, topNSize);
		}
		return new TreeSet<WordEntry>(wordEntrys);
	}

	public static void main(String[] args) throws IOException {
		WordAnalogy wa = new WordAnalogy("models/corpus_new.txt.seg.jbin", 10);
		wa.loadJW2VModel();
		System.out.println(wa.analogy("hà_nội", "hà_đông", "ba_đình"));
	}
}
