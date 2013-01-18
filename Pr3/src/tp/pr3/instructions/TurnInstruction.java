package tp.pr3.instructions;

import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.Rotation;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public class TurnInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena)  throws WrongInstructionFormatException{
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(TURN)||arrayInstruction[0].equalsIgnoreCase(GIRAR))) {
			switch (arrayInstruction[1].toUpperCase()) {
				case RIGHT: this.rotation = Rotation.RIGHT; return this;
				
				case LEFT: this.rotation = Rotation.LEFT; return this;
				
				default: throw new WrongInstructionFormatException();
			}
		} 
		else /* cadena que no tenía dos palabras */ throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " TURN | GIRAR < LEFT|RIGHT >";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		navigation = this.navigation;
	}

	@Override
	public void execute() {
		this.navigation.rotate(rotation);
	}
	private static final String RIGHT = "RIGHT";
	private static final String LEFT = "LEFT";
	private static final String TURN = "TURN";
	private static final String GIRAR = "GIRAR";
	private NavigationModule navigation;
	private Rotation rotation;

}
