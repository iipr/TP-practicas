package tp.pr5.console;

import java.util.Scanner;

import tp.pr5.Controller;
import tp.pr5.EscribeConsola;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

public class ConsoleController extends Controller {

	// Constructor que llama a la clase padre
	public ConsoleController(RobotEngine game) {
		super(game);
	}

	@Override
	public void startController() {
		this.engine.requestStart();
		Scanner sc = new Scanner(System.in);
		while (!this.engine.isOver()) {
			EscribeConsola.prompt(); // Muestra por consola: WALL·E>
			try {
				// Genera una instrucion a partir de la cadena leída y se la envía al robot para que la ejecute
				this.engine.communicateRobot(Interpreter.generateInstruction(sc.nextLine()));	
			} catch (WrongInstructionFormatException e) {
				engine.saySomething(EscribeConsola.NOT_UNDERSTAND);
			}
		}
		sc.close(); // Cierra el escaner
	}

}