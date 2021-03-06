package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class MoveInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(MOVE) || arrayInstruction[0].equalsIgnoreCase(MOVER)))
			return new MoveInstruction();
		else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " MOVE | MOVER";
	}

	@Override
	public void configureContext(RobotEngine engine,
		NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
		this.robot = engine;
	}
	
	@Override
	public void execute() throws InstructionExecutionException {
		this.navigation.move();	
		this.robot.addFuel(-5); //Actualiza el fuel al moverse.	
		if(navigation.atSpaceship()) robot.endOfGame();
	
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
		navigation.undoMove();
		this.robot.addFuel(5);
	}
	
	public String toString() {
		return "Move";
	}
	
	private RobotEngine robot;
	private NavigationModule navigation;
	private static final String MOVE = "MOVE";
	private static final String MOVER = "MOVER";
	
}
