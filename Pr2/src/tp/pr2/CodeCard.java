package tp.pr2;

public class CodeCard extends Item {
	//Constructor de la tarjeta, que llama a la clase padre para completarse
	public CodeCard(String id, String description, String code) {
		super(id, description);
		this.code = code;
	}
		
	//TRUE SIEMPRE, una tarjeta se puede usar tantas veces como se quiera
	public boolean canBeUsed() {
		return true;
	}
	
	//Si el robot esta en un lugar mirando en una direccion donde hay una puerta en una calle, usa la tarjeta
	//Devuelve true si se pudo abrir o cerrar la calle con la tarjeta
	//La puerta se abre si el codigo de la tarjeta coincide con el de la calle
	public boolean use(RobotEngine r, Place p) {
		Street calle = r.getHeadingStreet();
		if (calle == null)
			return false;
		else {
			if (calle.isOpen() && calle.close(this))
				return true;
			else if (!calle.isOpen() && calle.open(this))
				return true;
			else
				return false;
		}
	}
	
	//Devuelve el código de la tarjeta.
	public String getCode(){
		return this.code;
	}
	
	private String code; 	//Code := Codigo secreto guardado en la tarjeta para abrir las puertas	
}