package chaosSimulatorPlotter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		//setup vars
		int width = 800;
		int height = 800;
		int maxTicks = 100000;
		
		int posArraySize = 1000;
		
		World world = new World(posArraySize);
		
		//set world vars
		Logic.maxForce = 1000;
		world.setHomeX(400);
		world.setHomeY(400);
		world.setDefaultCoef(10);
		world.setHomeCoef(10);
		world.setFricition(.95);
		world.setMaxStopDist(15);
		world.setHomeX(width/2);
		world.setHomeY(height/2);
		
		//set plot vars
		int minX = 0;
		int maxX = 800;
		int minY = 0;
		int maxY = 800;
		
		int resX = 10;
		int resY = 10;
		
		int fps = 60;
		
		//generate points
		double gapX = ((double)maxX - minX)/(resX - 1);
		double gapY = ((double)maxY - minY)/(resY - 1);
		double spaceX = 0;
		double spaceY = 0;
		
		
		int numPoints = resX*resY;
		double[][] points = new double[numPoints][2];
		
		for (int i = 0; i < resX; i++) {
			for (int j = 0; j < resY; j++) {
				points[i*resX+j][0] = i*gapX+minX+spaceX;
				points[i*resX+j][1] = j*gapY+minY+spaceY;
				
			}
		}		
		//System.out.println(Arrays.deepToString(points));
		
		//open file
		PrintWriter writer = new PrintWriter("output.txt","UTF-8");
		PrintWriter logWriter = new PrintWriter("log.txt","UTF-8");
		
		//start timer
		final long startTime = System.currentTimeMillis();
		
		//plot points
		double[][] finalPos = new double[numPoints][2];
		int[] tickCounter = new int[numPoints];
		boolean stopped;
		for (int i = 0; i < numPoints; i++) {
			
			//for each point...
			//reset world
			world.resetWorld();
			
			//set up world
			world.setVelX(0);
			world.setVelY(0);
			world.setArmX(points[i][0]);
			world.setArmY(points[i][1]);
			
			tickCounter[i] = 0;
			
			stopped = false;
			while (!stopped) {
				world.tick(fps);
				
				tickCounter[i]++;
				
				if (tickCounter[i] > maxTicks) {
					System.out.println("error: max ticks hit");
					System.out.println(world.getArmX());
					System.out.println(world.getArmX());
					System.out.println(Arrays.toString(world.getPosArrayX()));
					System.out.println(Arrays.toString(world.getPosArrayY()));
					logWriter.println("error: max ticks hit");
					logWriter.println(world.getArmX());
					logWriter.println(world.getArmX());
					logWriter.println(Arrays.toString(world.getPosArrayX()));
					logWriter.println(Arrays.toString(world.getPosArrayY()));
					
					
					finalPos[i][0] = -100;
					finalPos[i][1] = -100;
					stopped = true;
				}
				
				if (world.getStopped() == true) {
					stopped = true;
					finalPos[i][0] = world.getArmX();
					finalPos[i][1] = world.getArmY();
					
				}
			}
			
			
			//print
			System.out.println("point " +i+": "+Arrays.toString(points[i]) + " took "+tickCounter[i]+" ticks: "+Arrays.toString(finalPos[i]));
			logWriter.println("point " +i+": "+Arrays.toString(points[i]) + " took "+tickCounter[i]+" ticks: "+Arrays.toString(finalPos[i]));
			
			//write to file
			writer.println("["+points[i][0]+", "+points[i][1]+", "+finalPos[i][0]+", "+finalPos[i][1]+"]");
			
			
			
		
		}
		//stop timer
		final long endTime = System.currentTimeMillis();
		int execTime = ((int)endTime - (int)startTime) / 1000;
		double timePerPoint = (double)execTime/numPoints;
		System.out.println("program took: "+execTime+" s, "+timePerPoint+" s per point");
		logWriter.println("program took: "+execTime+" s, "+timePerPoint+" s per point");
		
		//close file
		writer.close();
		logWriter.close();
		System.out.println("data in output.txt");
	
	
	
	}

}
