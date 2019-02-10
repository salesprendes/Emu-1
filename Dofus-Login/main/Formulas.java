package main;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class Formulas 
{
	static char[] HASH = new char[]
	{
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
		'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
	};
	private static final AtomicReference<Random> random = new AtomicReference<>(new SecureRandom());
	private static final String abecedario = "abcdefghijklmnopqrstuvwxyz";
	
	public static String get_Desencriptar_Password(final String key, final String password)
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
	
	private static char get_Random_Abecedario() 
	{
        return abecedario.charAt(random.get().nextInt(abecedario.length()));
    }
	
	public static String generar_Key() 
	{
		StringBuilder sb = new StringBuilder(32);
		IntStream.range(0, 32).forEach(valor -> sb.append(get_Random_Abecedario()));
		return sb.toString();
	}
}
