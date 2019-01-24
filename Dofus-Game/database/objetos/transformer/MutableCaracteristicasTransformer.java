package database.objetos.transformer;

import objetos.entidades.caracteristicas.DefaultCaracteristicas;
import objetos.entidades.caracteristicas.MutableCaracteristicas;

final public class MutableCaracteristicasTransformer implements Transformer<MutableCaracteristicas>
{
	final private CaracteristicasTransformer inner = new CaracteristicasTransformer();
	
	public String serializar(MutableCaracteristicas valor)
	{
		return inner.serialize(valor);
	}

	public MutableCaracteristicas deserializar(String serializador)
	{
		MutableCaracteristicas caracteristicas = inner.unserialize(serializador);
		
		if (caracteristicas == null) 
		{
            return new DefaultCaracteristicas();
        }
		
		return caracteristicas;
	}

}
