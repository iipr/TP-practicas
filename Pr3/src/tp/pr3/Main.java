package tp.pr3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import tp.pr3.cityLoader.CityLoaderFromTxtFile;
import tp.pr3.items.CodeCard;
import tp.pr3.items.Fuel;
import tp.pr3.items.Garbage;

/**
 * Aplicación que utiliza las clases de la práctica sobre el mapa
 * que aparece como ejemplo en el enunciado.
 * 
 * @author PuriArenas
 * @y.exclude
 */
public class Main {
	/**
	 * Creates the city, the engine and finally
	 * starts the simulation
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		if (args.length == 0){
			Escribe.llamadaIncorrecta();
			System.exit(1);
		}
		FileInputStream input = null;
		try {
			input = new FileInputStream(args[0]);
		} catch (FileNotFoundException e) {
			Escribe.noExisteFichero(args[0]);
			System.exit(2);
		}
		CityLoaderFromTxtFile cityLoader = new CityLoaderFromTxtFile();
		City city = null;
		try {
			city = cityLoader.loadCity(input);
		} catch (IOException e) {
			//TODO
		}
		RobotEngine engine = 
				new RobotEngine(city, cityLoader.getInitialPlace(), Direction.NORTH);
		// plays
		engine.startEngine();
		
	}
}