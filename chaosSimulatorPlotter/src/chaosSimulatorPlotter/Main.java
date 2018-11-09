package chaosSimulatorPlotter;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
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
		int gapX = (maxX - minX)/(resX - 1);
		int gapY = (maxY - minY)/(resY - 1);
		
		int numPoints = resX*resY;
		int[][] points = new int[numPoints][2];
		
		for (int i = 0; i < resX; i++) {
			for (int j = 0; j < resY; j++) {
				points[i*resX+j][0] = i*gapX+minX;
				points[i*resX+j][1] = j*gapY+minY;
				
			}
		}		
		//System.out.println(Arrays.deepToString(points));
	
		//plot points
		double[][] finalPos = new double[numPoints][2];
		int[] tickCounter = new int[numPoints];
		boolean stopped;
		for (int i = 0; i < numPoints; i++) {
			System.out.println("generating point "+i+": "+Arrays.toString(points[i]));
			
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
					finalPos[i][0] = Math.round(world.getArmX());
					finalPos[i][1] = Math.round(world.getArmY());
					
				}
			}
			
			
			//print/send to file
			System.out.println("final took "+tickCounter[i]+" ticks : "+Arrays.toString(finalPos[i]));
			
		}
		
	
	
	
	}

}
