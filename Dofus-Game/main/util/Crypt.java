package main.util;

import java.security.InvalidParameterException;

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
	
	public static int get_ordinal(char c) 
	{
		if (c >= 'a' && c <= 'z') 
			return c - 'a';

		if (c >= 'A' && c <= 'Z') 
			return c - 'A' + 26;

		if (c >= '0' && c <= '9') 
			return c - '0' + 52;

		switch (c) 
		{
			case '-':
				return 62;
				
			case '_':
				return 63;
				
			default:
				throw new InvalidParameterException("Char invalido " + c);
		}
	}
	
	public static String get_codificar_String(short valor, final int anchura) 
	{
        char[] encoded = new char[anchura];

        for (int i = anchura - 1; i >= 0; --i) 
        {
            encoded[i] = HASH[valor & 63];
            valor >>= 6;
        }
        
        return new String(encoded);
    }
}
