package edu.yu.introtoalgs;

public class EstimateSecretAlgorithmsClient {
	
	public static void main(String[] args) {
		SecretAlgorithm1 a1 = new SecretAlgorithm1();
		SecretAlgorithm2 a2 = new SecretAlgorithm2();
		SecretAlgorithm3 a3 = new SecretAlgorithm3();
		SecretAlgorithm4 a4 = new SecretAlgorithm4();
		
		double t0, t1, t2, t3, t4, t5; // variables for 3 time differences
		double[] times = new double[20]; // store the times to calculate ratios easily
		times[0] = Double.MIN_VALUE; // so that first ratio doesn't throw divideByZero error
		int experimentNumber;
		
		System.out.println("**********\nSecretAlgorithm1 Results\n**********");
		experimentNumber = 1;
		for (int n = 32; n < 9000; n*=2) {
			a1.setup(n);
			t0 = System.currentTimeMillis();
			a1.execute();
			t1 = System.currentTimeMillis();
			
			t2 = System.currentTimeMillis();
			a1.execute();
			t3 = System.currentTimeMillis();
			
			t4 = System.currentTimeMillis();
			a1.execute();
			t5 = System.currentTimeMillis();
			
			times[experimentNumber] = ( (t1 - t0) + (t3 - t2) + (t5 - t4) ) / 3;
			double ratio = times[experimentNumber] / times[experimentNumber - 1];
			System.out.printf("%d (n)   %f time (ms)   %f (ratio) \n", n, times[experimentNumber], ratio);
			experimentNumber++;
		}
		
		System.out.println("**********\nSecretAlgorithm2 Results\n**********");
		experimentNumber = 1;
		for (int n = (int) Math.pow(2, 15); n < (int) Math.pow(2, 28); n*=2) {
			a2.setup(n);
			t0 = System.currentTimeMillis();
			a2.execute();
			t1 = System.currentTimeMillis();
			
			t2 = System.currentTimeMillis();
			a2.execute();
			t3 = System.currentTimeMillis();
			
			t4 = System.currentTimeMillis();
			a2.execute();
			t5 = System.currentTimeMillis();
			
			times[experimentNumber] = ( (t1 - t0) + (t3 - t2) + (t5 - t4) ) / 3;
			double ratio = times[experimentNumber] / times[experimentNumber - 1];
			System.out.printf("%d (n)   %f time (ms)   %f (ratio) \n", n, times[experimentNumber], ratio);
			experimentNumber++;
		}
			
		System.out.println("**********\nSecretAlgorithm3 Results\n**********");
		experimentNumber = 1;
		for (int n = (int) Math.pow(2, 11); n < (int) Math.pow(2, 20); n*=2) {
			a3.setup(n);
			t0 = System.currentTimeMillis();
			a3.execute();
			t1 = System.currentTimeMillis();
			
			t2 = System.currentTimeMillis();
			a3.execute();
			t3 = System.currentTimeMillis();
			
			t4 = System.currentTimeMillis();
			a3.execute();
			t5 = System.currentTimeMillis();
			
			times[experimentNumber] = ( (t1 - t0) + (t3 - t2) + (t5 - t4) ) / 3;
			double ratio = times[experimentNumber] / times[experimentNumber - 1];
			System.out.printf("%d (n)   %f time (ms)   %f (ratio) \n", n, times[experimentNumber], ratio);
			experimentNumber++;
		}
		
		System.out.println("**********\nSecretAlgorithm4 Results\n**********");
		experimentNumber = 1;
		for (int n = (int) Math.pow(2, 17); n < (int) Math.pow(2, 28); n*=2) {
			a4.setup(n);
			t0 = System.currentTimeMillis();
			a4.execute();
			t1 = System.currentTimeMillis();
			
			t2 = System.currentTimeMillis();
			a4.execute();
			t3 = System.currentTimeMillis();
			
			t4 = System.currentTimeMillis();
			a4.execute();
			t5 = System.currentTimeMillis();
			
			times[experimentNumber] = ( (t1 - t0) + (t3 - t2) + (t5 - t4) ) / 3;
			double ratio = times[experimentNumber] / times[experimentNumber - 1];
			System.out.printf("%d (n)   %f time (ms)   %f (ratio) \n", n, times[experimentNumber], ratio);
			experimentNumber++;
		}
		

	}
}
	


