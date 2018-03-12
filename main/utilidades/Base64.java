package main.utilidades;

import java.security.InvalidParameterException;

final public class Base64 
{
	static public int convertir_Char_Int(char caracter_char)
	{
		if (caracter_char >= 'a' && caracter_char <= 'z') 
		{
			return caracter_char - 'a';
		}
		if (caracter_char >= 'A' && caracter_char <= 'Z')
		{
			return caracter_char - 'A' + 26;
		}
		if (caracter_char >= '0' && caracter_char <= '9')
		{
			return caracter_char - '0' + 52;
		}
		switch (caracter_char) 
		{
			case '-':
			return 62;

			case '_':
			return 63;

			default:
			throw new InvalidParameterException("Char invalido");
		}
	}
}
