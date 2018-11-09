package chaosSimulatorPlotter;

public class DefaultSetups {
	public DefaultSetups() {
		
	}
	
	public static void setup1(World world) {
		world.addMagnet(new Magnet(350,350,world.getDefaultCoef()));
		world.addMagnet(new Magnet(450,350,world.getDefaultCoef()));
		world.addMagnet(new Magnet(450,450,world.getDefaultCoef()));
		world.addMagnet(new Magnet(350,450,world.getDefaultCoef()));
		
		world.addMagnet(new Magnet(350,400,-world.getDefaultCoef()));
		world.addMagnet(new Magnet(450,400,-world.getDefaultCoef()));
		world.addMagnet(new Magnet(400,350,-world.getDefaultCoef()));
		world.addMagnet(new Magnet(400,450,-world.getDefaultCoef()));
	}
}
