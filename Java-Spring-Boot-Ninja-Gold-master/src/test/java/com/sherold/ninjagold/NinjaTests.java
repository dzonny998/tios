package com.sherold.ninjagold;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NinjaTests {
	
	
	@Test
    public void testGenerateGoldFarm() {
        Ninja ninja = new Ninja();
        ninja.generateGold("farm");

        assertTrue(ninja.getGold() >= 10 && ninja.getGold() <= 20);
        assertFalse(ninja.getActivities().isEmpty());
    }
	
	@Test
    public void testGenerateGoldCave() {
        Ninja ninja = new Ninja();
        ninja.generateGold("cave");

        assertTrue(ninja.getGold() >= 5 && ninja.getGold() <= 10);
        assertFalse(ninja.getActivities().isEmpty());
    }
	
	@Test
    public void testGenerateGoldHouse() {
        Ninja ninja = new Ninja();
        ninja.generateGold("house");

        assertTrue(ninja.getGold() >= 2 && ninja.getGold() <= 5);
        assertFalse(ninja.getActivities().isEmpty());
    }
	
	@Test
    public void testGenerateGoldCasino() {
        Ninja ninja = new Ninja();
        ninja.generateGold("casino");

        assertFalse(ninja.getActivities().isEmpty());
    }
	
	 @Test
	    public void testGenerateGoldSpa() {
	        Ninja ninja = new Ninja();
	        ninja.generateGold("spa");

	        assertFalse(ninja.getActivities().isEmpty());
	    }
	
}