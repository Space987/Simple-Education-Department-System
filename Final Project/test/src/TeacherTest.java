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
public class TeacherTest extends Teacher {

    public TeacherTest() {
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

        String testDegree = "PhD";
        Teacher instance = new Teacher(103, "Frank", 44, "Male", testDegree, "WWI", 3);
        double expResult = 6128.64;

        double result = instance.computePayRoll();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");

    }

}
