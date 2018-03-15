package main;

public class Formulas 
{
	static char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
	
	public static String desencriptar_Password(final String key, final String password)
	{
		StringBuilder password_encriptada = new StringBuilder(password.length() / 2);
		password_encriptada.append("#1");
		
		for (int i = 0; i < password.length(); i += 2)
		{
			int encode_c1 = password.charAt(i) / 16 + key.charAt(i % key.length());
			int encode_c2 = password.charAt(i) % 16 + key.charAt(i % key.length());

			password_encriptada.append(HASH[(encode_c1 % HASH.length)]);
			password_encriptada.append(HASH[(encode_c2 % HASH.length)]);
		}
		return password_encriptada.toString();
	}
}
