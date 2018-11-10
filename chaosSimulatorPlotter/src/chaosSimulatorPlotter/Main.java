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
		
		int numPoints = resX*resY;
		double[][] points = new double[numPoints][2];
		
		for (int i = 0; i < resX; i++) {
			for (int j = 0; j < resY; j++) {
				points[i*resX+j][0] = i*gapX+minX;
				points[i*resX+j][1] = j*gapY+minY;
				
			}
		}		
		//System.out.println(Arrays.deepToString(points));
		
		//open file
		PrintWriter writer = new PrintWriter("output.txt","UTF-8");
		
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
				
				if (world.getStopped() == true) {
					stopped = true;
					finalPos[i][0] = world.getArmX();
					finalPos[i][1] = world.getArmY();
					
				}
			}
			
			
			//print
			System.out.println("point " +i+": "+Arrays.toString(points[i]) + " took "+tickCounter[i]+" ticks: "+Arrays.toString(finalPos[i]));
			
			
			//write to file
			writer.println("["+points[i][0]+", "+points[i][1]+", "+finalPos[i][0]+", "+finalPos[i][1]+"]");
			
			
			
		
		}
		
		//close file
		writer.close();
		System.out.println("data in output.txt");
	
	
	
	}

}
