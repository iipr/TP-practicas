package tp.pr3.instructions;

import tp.pr3.Escribe;
import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.instructions.exceptions.InstructionExecutionException;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.Item;
import tp.pr3.items.ItemContainer;

public class ScanInstruction implements Instruction{
	public ScanInstruction(){
		this.id = null;
		this.robotContainer = null;
	}
	public ScanInstruction(String id){
		this.id = id;
		this.robotContainer = null;
	}

	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(SCAN) || arrayInstruction[0].equalsIgnoreCase(ESCANEAR))){
			return new ScanInstruction();
		}
		else if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(SCAN) || arrayInstruction[0].equalsIgnoreCase(ESCANEAR))){
			return new ScanInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}

	public String getHelp() {
		return " SCAN|ESCANEAR [id]";
	}

	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.robotContainer = robotContainer;
	}

	public void execute() throws InstructionExecutionException{
		if(this.robotContainer.numberOfItems() == 0) 
			throw new InstructionExecutionException(Escribe.INV_EMPTY); //say("My inventory is empty");(necesaria excepcion para tests)
		else if (this.id == null) 
			Escribe.say(Escribe.CARRYING_ITEMS + this.robotContainer.toString());
		else{
			Item item = this.robotContainer.getItem(id);
			if (item == null)
				throw new InstructionExecutionException(Escribe.NOT_HAVE_THE_OBJECT.replace("<id>", id));
			else 
				Escribe.say(item.toString()); //say(item.toString());
		}
		
	}
	private String id;
	private ItemContainer robotContainer;
	
	private static final String SCAN = "SCAN";
	private static final String ESCANEAR = "ESCANEAR";
}
