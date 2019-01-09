package main.util;

public class Crypt
{
	private static final char[] HASH = new char[]
	{ 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
		'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
		'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
	};
	
	public static int get_Index_Desde_Hash(char ch) 
	{
		for (int i = 0; i < HASH.length; i++)
			if (HASH[i] == ch)
				return i;
		throw new ArrayIndexOutOfBoundsException(ch + " no está en la matriz de hash");
	}
	
	public static char get_Hash_Desde_Index(int i) 
	{
		return HASH[i];
	}
}
