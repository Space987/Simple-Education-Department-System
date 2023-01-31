/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author natan
 */
public class StaffTest {

    public StaffTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testComputePayRoll() {
        int hours = 18;
        Staff instance = new Staff(11, "Natan", 18, "Male", "Janitor", hours, 1); 
        double expResult = 979.2;
        
        double result = instance.computePayRoll();      
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

}
