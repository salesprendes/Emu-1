package main;

public class Formulas 
{
	static char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
	
	public static String desencriptar_Password(final String Key, final String password)
	{
		StringBuilder _loc4_ = new StringBuilder();
		_loc4_.append("#1");
		
		for(int _loc5_ = 0; _loc5_ < password.length(); _loc5_++)
		{
			char pass = password.charAt(_loc5_);
			char key = Key.charAt(_loc5_);

			int APass = (int)pass / 16;
			int AKey = (int)pass % 16;
			int ANB = (APass + (int)key) % HASH.length;
			int ANB2 = (AKey + (int)key) % HASH.length;

			_loc4_.append(HASH[ANB]);
			_loc4_.append(HASH[ANB2]);
		}
		return _loc4_.toString();
	}
}