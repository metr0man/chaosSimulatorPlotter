package chaosSimulatorPlotter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main{
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		//thread vars
		int numThreads = 4;
		
		//setup vars
		int minX = 0;
		int maxX = 800;
		int minY = 0;
		int maxY = 800;
		
		int resX = 20;
		int resY = 20;

		//define vars for later
		int numPoints = resX*resY;
		double gapX = ((double)maxX - minX)/(resX - 1);
		double gapY = ((double)maxY - minY)/(resY - 1);
		int spaceX = 0; //keep at zero
		int spaceY = 0; //keep at zero
		
		//start timer
		final long startTime = System.currentTimeMillis();
		
		//threads...
		//generate points
		double totalPoints[][] = new double[numPoints][2];
		for (int i = 0; i < resX; i++) {
			for (int j = 0; j < resY; j++) {
				totalPoints[i*resX+j][0] = i*gapX+minX+spaceX;
				totalPoints[i*resX+j][1] = j*gapY+minY+spaceY;
			}
		}
		
		//divide points
		double threadPoints[][][] = new double[numThreads][2][2];
		double pointsPerThread = (double)numPoints/numThreads;
		for(int i = 0; i < numThreads; i++) {
			int startIndex = (int)(pointsPerThread*i);
			int endIndex = (int)(pointsPerThread*(i+1));
			threadPoints[i] = Arrays.copyOfRange(totalPoints, startIndex, endIndex);
		}
		
		//create threads 
		System.out.println("creating "+numThreads+" threads");
		Generate threadArray[] = new Generate[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threadArray[i] = new Generate(threadPoints[i],i);
			threadArray[i].start();
		}		
		
		//wait for threads to finish
		boolean active = true;
		while (active) {
			active = false;
			for (int i = 0; i < numThreads; i++) {
				if (!threadArray[i].finished) {
					active = true;
				}
			}
			//sleep to use less cpu
			int sleepTime = 100; //in ms
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//stop timers
		final long endTime = System.currentTimeMillis();
		double execTime = (endTime - startTime)/(double)1000;
		double timePerPoint = execTime/numPoints;
		System.out.println("program took: "+execTime+" s, "+timePerPoint+" s per point");
		
		//sleep a little bit because problems?
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//write data
		int lenPoints = 0;
		PrintWriter writer = new PrintWriter("output.txt","UTF-8");
		for (int i = 0; i < numThreads; i++) {
			for (int j = 0; j < threadArray[i].output.length; j++) {
				writer.println(Arrays.toString(threadArray[i].output[j]));
				lenPoints++;
			}
		}
		writer.close();
		System.out.println(lenPoints+" points in output.txt");
		
		
		
		
		
		
		//writer   OLD
		/*System.out.println("writing to file");
		PrintWriter writer = new PrintWriter("output.txt","UTF-8");
		for(int i = 0; i < output.length; i++) {
			writer.println("["+output[i][0]+", "+output[i][1]+", "+output[i][2]+", "+output[i][3]+"]");
		}
		writer.close();
		*/
		
		//logWriter.println("program took: "+execTime+" s, "+timePerPoint+" s per point");
		
		//close file
		//writer.close();
		//logWriter.close();
		//System.out.println("data in output.txt");
	}
}
