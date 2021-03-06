package tp.pr1;


	// Lugar: Consta de un nombre, una descripción y un booleano que indica si hay nave en él.

public class Place {

	public Place(String name, boolean isSpaceShip, String description) {
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
	}

	public boolean isSpaceship() {
		return this.isSpaceShip;
	}

	// Une el título con la descripción para mostrar un lugar por pantalla.

	public String toString() {
		return this.name + LINE_SEPARATOR + this.description;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private String name;
	private boolean isSpaceShip;
	private String description;
}
