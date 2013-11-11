package edu.ktlab.w2v.distance;

public class CosineDistance implements Distance {
	@Override
	public float distance(float[] x, float[] y) {
		if (x.length != y.length)
			return 0f;
		double xx = 0.0;
		double yy = 0.0;
		double xy = 0.0;

		for (int i = 0; i < x.length; i++) {
			double x1 = x[i];
			double y1 = y[i];
			xx += x1 * x1;
			yy += y1 * y1;
			xy += x1 * y1;
		}
		if (xx == 0 || yy == 0) {
			return 0f;
		} else {
			return (float) (xy / Math.sqrt(xx * yy));
		}
	}
	
	public static void main(String...strings) {
		CosineDistance cosine = new CosineDistance();
		float[] x = {1f, 1f, 2f};
		float[] y = {1f, 1f, 1f};
		System.out.println(cosine.distance(x, y));
	}
}
