package main.utilidades;

import java.util.Random;

final public class RandomString 
{
	final private Random random;
    final private CharSequence char_sequencia;
    
    public RandomString(Random _random, CharSequence _char_sequencia)
    {
        random = _random;
        char_sequencia = _char_sequencia;
    }
    
    public String generar_String(int longitud) 
    {
        char[] buffer = new char[longitud];
        for (int i = 0; i < longitud; ++i) 
        {
            buffer[i] = char_sequencia.charAt(random.nextInt(char_sequencia.length()));
        }
        return new String(buffer);
    }
}
