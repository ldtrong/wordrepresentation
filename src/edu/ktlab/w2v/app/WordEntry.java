package edu.ktlab.w2v.app;

public class WordEntry implements Comparable<WordEntry> {
	public String name;
	public float[] vector;
	public float score;

	public WordEntry(String name, float score) {
		this.name = name;
		this.score = score;
	}
	
	public WordEntry(String name, float score, float[] vector) {
		this.name = name;
		this.score = score;
		this.vector = vector;
	}

	@Override
	public String toString() {
		return this.name + "\t" + score;
	}

	@Override
	public int compareTo(WordEntry o) {
		if (this.score < o.score) {
			return 1;
		} else {
			return -1;
		}
	}

}