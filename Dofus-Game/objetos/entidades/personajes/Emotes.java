package objetos.entidades.personajes;

final public class Emotes
{
	private int emotes;

	public Emotes(final int _emotes)
	{
		emotes = _emotes;
	}
	
	//true para agregar, false para quitarlo.
	public boolean get_Modificar_Emote(final Emote emote, final boolean metodo)
	{
		final int valor = (int) Math.pow(2, emote.get_Emote() - 1);
		
		if ((emotes & valor) == 0)
		{
			if(metodo)
				emotes += emote.get_Emote();
			else
				emotes -= emote.get_Emote();
			
			if (emotes < 0)
				emotes = 0;
			else if (emotes > 7667711)
				emotes = 7667711;
			
			return true;
		}
		
		return false;
	}

	public int get() 
	{
		return emotes;
	}

	public enum Emote
	{
		SENTARSE (1),
		SENAL_CON_LA_MANO (2),
		APLAUDIR (4),
		ENFADARSE (8),
		MOSTRAR_MIEDO (16),
		MOSTRAR_ARMA (32),
		TOCAR_LA_FLAUTA (64),
		TIRARSE_UN_PEDO (128),
		SALUDAR (256),
		BESO (512),
		PIEDRA (1024),
		HOJA (2048),
		TIJERAS (4096),
		CRUZARSE_BRAZOS (8192),
		SENALAR_CON_DEDO (16384),
		CUERVO (32768),
		ACOSTARSE (262144),
		CAMPEON (1048576),
		AURA_PODER (2097152),
		AURA_VAMPIRICA (4194304);

		private int emote;

		private Emote(final int _emote)
		{
			emote = _emote;
		}

		public int get_Emote()
		{
			return emote;
		}
	}
}
