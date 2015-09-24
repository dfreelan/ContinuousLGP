/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

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
public class SimplexTest {
    
    public SimplexTest() {
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
     * Test of Initialize method, of class Simplex.
     */
    @Test
    public void testInitialize() {
        //System.out.println("Initialize");
        //Simplex instance = null;
        //instance.Initialize();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of mutate method, of class Simplex.
     */
    @Test
    public void testMutate() {
        System.out.println("mutate");
        float strength = 0.0F;
        Simplex instance = new Simplex(3);
        float[] expResult = null;
        float[] result = instance.mutate(strength);
        
//assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of changeStrength method, of class Simplex.
     */
    @Test
    public void testChangeStrength() {
        System.out.println("changeStrength");
        float strength = 0.0F;
        Simplex instance = new Simplex(3);
        float[] expResult = null;
        instance.mutate(.1f);
        float[] result = instance.changeStrength(strength);
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of applyMutation method, of class Simplex.
     */
    @Test
    public void testApplyMutation() {
        System.out.println("applyMutation");
        float strength = .005f;
        Simplex instance = new Simplex(3);
        instance.weights = new float[] {.1f,.1f,.8f};
        instance.mutateVector = new float[] {.1f, .1f,.8f};
        instance.applyMutation(strength);
        FloatMath.printFloatArr(instance.weights);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The+++ test case is a prototype.");
    }
    
}
