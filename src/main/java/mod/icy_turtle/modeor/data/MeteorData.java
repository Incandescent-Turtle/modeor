package mod.icy_turtle.modeor.data;

import java.util.Arrays;

public class MeteorData
{
	// random one from oct 5th 2025
	public static final MeteorData DEFAULT = new MeteorData("2018 SP1)", 142.5738833281, 63.7609789875, 58973.7194317699, 5041878.253037102, 23.1, false);

	public MeteorData(){}
	public MeteorData(String name, double diameterMetersMax, double diameterMetersMin, double velocityKps, double missDistanceKm, double magnitude, boolean hazardous)
	{
		this.name = name;
		this.diameterMetersMax = diameterMetersMax;
		this.diameterMetersMin = diameterMetersMin;
		this.velocityKps = velocityKps;
		this.missDistanceKm = missDistanceKm;
		this.magnitude = magnitude;
		this.hazardous = hazardous;
	}
	public String name;
	public double diameterMetersMax;
	public double diameterMetersMin;
	public double velocityKps;
	public double missDistanceKm;
	public double magnitude;
	public boolean hazardous;

	@Override
	public String toString()
	{
        String hazard = hazardous ? "hazardous." : "not hazardous.";
		return "Meteor incoming with name " + name  + ", with a max diameter of " + diameterMetersMax + "m, min diameter of " + diameterMetersMin + "m, velocity " + velocityKps + "km/s, miss distance " + missDistanceKm + "km, magnitude " + magnitude + ", it is " + hazard;
	}
}
