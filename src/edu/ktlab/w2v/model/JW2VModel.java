package edu.ktlab.w2v.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class JW2VModel extends Model {
	public JW2VModel(HashMap<String, float[]> wordVectors, int words, int size) throws IOException {
		this.wordVectors = wordVectors;
		this.words = words;
		this.size = size;
	}

	public JW2VModel(String path) throws IOException {
		loadJW2VModel(path);
	}

	public void loadJW2VModel(String path) throws IOException {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(
				path)))) {
			words = dis.readInt();
			size = dis.readInt();
			//double len = 0;
			float vector = 0;

			String key = null;
			float[] value = null;
			for (int i = 0; i < words; i++) {
				key = dis.readUTF();
				value = new float[size];
				for (int j = 0; j < size; j++) {
					vector = dis.readFloat();
					//len += vector * vector;
					value[j] = vector;
				}

				//len = Math.sqrt(len);
				//for (int j = 0; j < size; j++) {
				//	value[j] /= len;
				//}
				wordVectors.put(key, value);
			}
		}
	}

	public void saveModel(File file) {
		try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(file)))) {
			dataOutputStream.writeInt(wordVectors.size());
			dataOutputStream.writeInt(size);
			for (Entry<String, float[]> element : wordVectors.entrySet()) {
				dataOutputStream.writeUTF(element.getKey());
				for (float d : element.getValue()) {
					dataOutputStream.writeFloat(d);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
