package tp.pr4.instructions;

import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.ItemContainer;

public class RadarInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(RADAR))){
			return new RadarInstruction();
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " RADAR";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
		this.engine = engine;
		
	}

	@Override
	public void execute() {
		navigation.radarCurrentPlace();
	}
	@Override
	public void undo() throws InstructionExecutionException {
		engine.lastInstruction().undo();
	}
	
	private NavigationModule navigation;
	private RobotEngine engine;
	
	private static final String RADAR = "RADAR";

}
