package tp.pr4;

import java.util.Scanner;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tp.pr4.gui.NavigationPanel;
import tp.pr4.gui.RobotPanel;
import tp.pr4.instructions.Instruction;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.Item;
import tp.pr4.items.ItemContainer;

public class RobotEngine {
	// Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.pilaInstruction = new Stack<Instruction>(); 
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.navigation.initHeading(direction);
		this.fuel = 100;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
		this.quit = false;
		this.robotPanel = null;
	}
	
	public void communicateRobot(Instruction instruction) {
		instruction.configureContext(this, this.navigation, this.itemContainer);
		try {
			instruction.execute();
			pilaInstruction.add(instruction);
		} catch (InstructionExecutionException exception) {
			if (modoConsola())
				EscribeConsola.mostrar(exception.getMessage());
			else {
				ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleError.png"));
				JOptionPane.showMessageDialog(robotPanel, exception.getMessage(), "¡Cuidado!", JOptionPane.OK_OPTION, icon);
			}
		}
	}

	public Street getHeadingStreet() {
		return this.navigation.getHeadingStreet();
	}

	public void requestQuit() {
		this.quit = true;
	}

	// Incrementa o decrementa la cantidad de fuel que tiene WALL·E 
	//Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
		if (modoConsola())
			EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
		else{
			robotPanel.actualizarFuel(this.fuel);
			if(!haveFuel()){
				ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleQuit.png"));
				JOptionPane.showMessageDialog(robotPanel, "I run out of fuel. I cannot move. Shutting down...", 
						"Bye, bye!", JOptionPane.OK_OPTION, icon);
				System.exit(0);
			}
		}
	}

	// Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight) {
		this.recycledMaterial += weight;
		if (modoConsola())
			EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
		else 
			robotPanel.actualizarRecycled(this.recycledMaterial);
	}

	// Para los tests
	public int getFuel() {
		return this.fuel;
	}

	// Para los tests
	public int getRecycledMaterial() {
		return this.recycledMaterial;
	}
	
	/* Devuelve el item indicado */
	public Item getItem(String id){
        return itemContainer.getItem(id);
	}

	// Muestra las instrucciones que reconoce WALL·E (Solo funciona en consola)
	public void requestHelp() {
		EscribeConsola.validInstructions(Interpreter.interpreterHelp());
	}

	// Escribe el estado de WALL·E
	public void printRobotState(int a) {
		if (modoConsola())EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
	}
	
	/* Indica si el programa se está ejecutando en modo consola */
	public boolean modoConsola(){
		return (this.robotPanel == null);
	}
	
	/* Indica si un item se ha gastado */
	
	public boolean itemGastado(String id) {
		if(itemContainer.containsItem(id))
			return false;
		else
			return true;
	}

	// Muestra los mendajes al iniciar el movimiento
	private void mostrarInicio() {
		EscribeConsola.currentPlace(this.navigation.getCurrentPlace());
		EscribeConsola.lookingDirection(this.navigation.getCurrentHeading());
		EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Devuelve true si WALL·E aun tiene combustible
	private boolean haveFuel() {
		return (this.fuel > 0);
	}

	// Devuelve true si es la nave espacial
	private boolean isSpaceship() {
		return this.navigation.atSpaceship();
	}
	
	/* Muestra el final por consola */
	
	private void mostrarFinal() {
		if (!haveFuel())
			EscribeConsola.say(EscribeConsola.OUT_OF_FUEL);
		else if (isSpaceship())							//Si se ha llegado a la nave, se muestra el mensaje correspondiente
			EscribeConsola.say(EscribeConsola.IN_SPACESHIP);
		else
			EscribeConsola.say(EscribeConsola.COMUNICATION_PROBLEMS);	// Se ha elegido la opción quit, luego se muestra el mensaje de despedida
	}
	
	/* Solo se llama desde la consola */
	
	public void startEngine() {
		mostrarInicio();
		Scanner sc = new Scanner(System.in);
		while (haveFuel() && !isSpaceship() && !quit) {
			EscribeConsola.prompt(); // Muestra por consola: WALL·E>
			try {
				// Genera una instrucion a partir de la cadena leída y se la envía al robot para que la ejecute
				communicateRobot(Interpreter.generateInstruction(sc.nextLine()));	
			} catch (WrongInstructionFormatException e) {
				EscribeConsola.say(EscribeConsola.NOT_UNDERSTAND);
			}
		}
		sc.close(); // Cierra el escaner
		mostrarFinal();
	}
	
	//Sets a panel to the navigation module in order to show its information in a GUI
	public void setNavigationPanel(NavigationPanel navPanel) {
		navigation.setNavigationPanel(navPanel);
	}
	
	//Sets a panel in order to show the robot information and the container in a GUI
	public void setRobotPanel(RobotPanel robotPanel) {
		this.robotPanel = robotPanel;
	}
	
	//Sets the main window of the GUI in order to inform about some robot events
	//public void setGUIWindow(MainWindow mainWindow) {} por ahora no se usa
	
	/** Devuelve la ultima instrucción apilada **/
	
	public Instruction lastInstruction() throws InstructionExecutionException{
		if (this.pilaInstruction.isEmpty()) 
			throw new InstructionExecutionException(EscribeConsola.NOT_MORE_INSTRUCTIONS);
		else 
			return  this.pilaInstruction.pop();
			// Devuelve la cima de la pila, eliminando la instrucción.
	}
	
	/* La ventana muestra el mensaje que se le pasa */
	public void darAvisoVentana(String mensaje){
		ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleError.png"));
		JOptionPane.showMessageDialog(robotPanel, mensaje, "", JOptionPane.OK_OPTION, icon);
	}

	/****************Operaciones de la tabla de ventana*************/
	
	public void	addItem(String id, String description) {
		if (!modoConsola())robotPanel.addItem(id, description);
	}
	
	public void deleteItem(String id) {
		if (!modoConsola())robotPanel.deleteItem(id);
	}
	
	public void deleteSelectedItem() { 
		if (!modoConsola())robotPanel.deleteSelectedItem();
	}
		
	private RobotPanel robotPanel;
	private Stack<Instruction> pilaInstruction;
	private NavigationModule navigation;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;
	private boolean quit;
}