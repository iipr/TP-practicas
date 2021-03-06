package tp.pr3.items.testprofesor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr3.items.CodeCard;
import tp.pr3.testprofesor.MockCodeCard;
import tp.pr3.testprofesor.MockPlace;
import tp.pr3.testprofesor.MockRobotEngine;
import tp.pr3.City;
import tp.pr3.Direction;
import tp.pr3.NavigationModule;
import tp.pr3.Place;
import tp.pr3.RobotEngine;
import tp.pr3.Rotation;
import tp.pr3.Street;

public class CodeCardTest {
	private CodeCard testItem;
	private RobotEngine testRobot;
	private Place testPlace;
	Street nonClosingStreet;
	Street openClosetreet;
	private NavigationModule testNavModule; 
	
	@Before
	public void setUp() throws Exception {
		testItem = new CodeCard(MockCodeCard.DEF_NAME, MockCodeCard.DEF_DESC, MockCodeCard.DEF_CODE);
		Place targetPlace = new Place ("Target Place", false, "Target place desc");
		testPlace = new Place ("Source Place", false, "Source place desc");
		nonClosingStreet = new Street(testPlace, Direction.NORTH, targetPlace, false, MockCodeCard.WRONG_CODE);
		openClosetreet = new Street(testPlace, Direction.NORTH, targetPlace, false, MockCodeCard.DEF_CODE);
		testRobot = new MockRobotEngine(null);
		Street [] streets = {nonClosingStreet};
		testNavModule = new NavigationModule(new City(streets), testPlace);
		testNavModule.initHeading(Direction.NORTH);
	}
	
	@Test
	public void testCanBeUsed() {
		assertTrue("ERROR: A codecard can be used more than once", testItem.canBeUsed());
		testItem.use(testRobot, testNavModule);
		assertTrue("ERROR: A codecard can be used more than once", testItem.canBeUsed());
	}
	
	@Test 
	public void testUse() {
		// Try with a street with a different code
		assertFalse("ERROR: Heading street has a code different from the code card but use returns true", testItem.use(testRobot, testNavModule));
		
		// Now try with a street with the same code
		Street [] streets = {openClosetreet};
		testRobot = new MockRobotEngine(openClosetreet); //TODO no estaba
		testNavModule = new NavigationModule(new City(streets), testPlace);
		testNavModule.initHeading(Direction.NORTH);
		assertTrue("ERROR: Heading street has the same code that the code card has but use returns false", testItem.use(testRobot, testNavModule));
		assertTrue("ERROR: Heading street has the same code that the code card has but use returns false", testItem.use(testRobot, testNavModule));

		
		// There is no heading street
		testNavModule.rotate(Rotation.LEFT);
		testRobot = new MockRobotEngine(null); //TODO no estaba
		assertFalse("ERROR: There is no Heading street but use returns true", testItem.use(testRobot, testNavModule));
	}
	
}
