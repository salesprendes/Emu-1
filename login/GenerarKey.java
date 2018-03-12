package login;

import java.security.SecureRandom;

import main.utilidades.Base64;
import main.utilidades.RandomString;

final public class GenerarKey
{
	/**
	 * https://github.com/Emudofus/Dofus/blob/1.29/ank/utils/Crypt.as#L20
	 */
	final private String hash_key;
	final static private RandomString random = new RandomString(new SecureRandom(),"abcdefghijklmnopqrstuvwxyz");
	
	public GenerarKey()
	{
        this(random.generar_String(32));
    }
	
	public GenerarKey(String _hash_key)
	{
		hash_key = _hash_key;
    }
	
	public String get_Hash_key() 
	{
        return hash_key;
    }
	
	public String desencriptar_Password(final String password) 
	{
		StringBuilder password_convertida = new StringBuilder(password.length() / 2);
		for (int _loc5_ = 0; _loc5_ < password.length(); _loc5_ += 2)
		{
			int k = hash_key.charAt(_loc5_ / 2) % 64;
			int d = Base64.convertir_Char_Int(password.charAt(_loc5_));
			int r = Base64.convertir_Char_Int(password.charAt(_loc5_ + 1));
			d -= k;
			r -= k;
			
			//si los valores son negativos los invierte
			while (d < 0) { d += 64; }
			while (r < 0) { r += 64; }

			int v = d * 16 + r;
			password_convertida.append((char) v);
		}
		return password_convertida.toString();
	}
}