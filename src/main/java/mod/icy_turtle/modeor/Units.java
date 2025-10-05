package mod.icy_turtle.modeor;

public class Units
{
	private Units(){}

	public static float GRAVITY_BPS = -MPSToBPS(9.81f);
	public static float MPSToBPS(float mps)
	{
		return mps / 20.0f;
	}

	public static float KMHToBPS(float kmh)
	{
		return MPSToBPS(kmh/3.6f);
	}
}
