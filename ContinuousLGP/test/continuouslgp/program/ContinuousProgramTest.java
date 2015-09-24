/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program;

import continuouslgp.Engine.FloatMath;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dfreelan
 */
public class ContinuousProgramTest {
    
    public ContinuousProgramTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of mutate method, of class ContinuousProgram.
     */
    @Test
    public void testMutate() {
        System.out.println("mutate");
        float strength = 0.5F;
        //(int length, int lineLength, int srcLength, int destLength, int instrType, int srcType, int destType){

        ContinuousProgram instance = new ContinuousProgram(5,5,3,3,0,0,0);
        System.out.println("before mutation");
        for(int i = 0; i<instance.length; i++){
            System.out.println("line " + i + ": ");
            FloatMath.printFloatArr(instance.getLineValues(i));
        }
        instance.mutate(strength);
        System.out.println("after mutation");
        for(int i = 0; i<instance.length; i++){
            System.out.println("line " + i + ": ");
            FloatMath.printFloatArr(instance.getLineValues(i));
        }
        
        instance.changeStrength(1.0f);
        System.out.println("after bigger mutation");
        for(int i = 0; i<instance.length; i++){
            System.out.println("line " + i + ": ");
            FloatMath.printFloatArr(instance.getLineValues(i));
        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of changeStrength method, of class ContinuousProgram.
     */
    @Test
    public void testChangeStrength() {
        System.out.println("changeStrength");
        float strength = 0.0F;
        ContinuousProgram instance = null;
        instance.changeStrength(strength);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getLineValues method, of class ContinuousProgram.
     */
    @Test
    public void testGetLineValues() {
        System.out.println("getLineValues");
        int line = 0;
        ContinuousProgram instance = null;
        float[][] expResult = null;
        float[][] result = instance.getLineValues(line);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
