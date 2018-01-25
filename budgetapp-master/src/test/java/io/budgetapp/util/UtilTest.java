package io.budgetapp.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testIsoDate() throws Exception {
        assertEquals(Util.toDate(LocalDate.of(2014, 8, 15)), Util.toDate("2014-08-15"));
		ASTClass.instrum("Expression Statement","13");
        assertEquals(Util.toDate(LocalDate.of(2016, 2, 29)), Util.toDate("2016-02-29"));
		ASTClass.instrum("Expression Statement","14");
    }

    @Test
    public void testBetweenDate() throws Exception {
        assertTrue(Util.betweenDate(Util.toDate("2014-08-19"), Util.toDate("2014-08-19"), Util.toDate("2014-08-19")));
		ASTClass.instrum("Expression Statement","19");
        assertTrue(Util.betweenDate(Util.toDate("2014-08-19"), Util.toDate("2014-08-19"), Util.toDate("2014-08-29")));
		ASTClass.instrum("Expression Statement","20");
        assertFalse(Util.betweenDate(Util.toDate("2014-08-18"), Util.toDate("2014-08-19"), Util.toDate("2014-08-29")));
		ASTClass.instrum("Expression Statement","21");
    }

    @Test
    public void testInMonth() throws Exception {
        assertTrue(Util.inMonth(Util.toDate("2014-08-19"), Util.toDate("2014-08-19")));
		ASTClass.instrum("Expression Statement","26");
        assertTrue(Util.inMonth(Util.toDate("2014-08-19"), Util.toDate("2014-08-01")));
		ASTClass.instrum("Expression Statement","27");
        assertTrue(Util.inMonth(Util.toDate("2014-08-19"), Util.toDate("2014-08-31")));
		ASTClass.instrum("Expression Statement","28");
        assertFalse(Util.inMonth(Util.toDate("2014-09-01"), Util.toDate("2014-08-31")));
		ASTClass.instrum("Expression Statement","29");
    }

    @Test
    public void testYesterday() {
        assertEquals(30, Util.yesterday(LocalDate.of(2014, 10, 1)));
		ASTClass.instrum("Expression Statement","34");
        assertEquals(14, Util.yesterday(LocalDate.of(2014, 10, 15)));
		ASTClass.instrum("Expression Statement","35");
        assertEquals(30, Util.yesterday(LocalDate.of(2014, 10, 31)));
		ASTClass.instrum("Expression Statement","36");
    }

    @Test
    public void testLastWeek() {
        assertEquals(52, Util.lastWeek(LocalDate.of(2014, 1, 1)));
		ASTClass.instrum("Expression Statement","41");
        assertEquals(53, Util.lastWeek(LocalDate.of(2014, 1, 7)));
		ASTClass.instrum("Expression Statement","42");
        assertEquals(1, Util.lastWeek(LocalDate.of(2014, 1, 8)));
		ASTClass.instrum("Expression Statement","43");
        assertEquals(52, Util.lastWeek(LocalDate.of(2014, 12, 31)));
		ASTClass.instrum("Expression Statement","44");
    }

    @Test
    public void testLastMonth() {
        assertEquals(12, Util.lastMonth(LocalDate.of(2014, 1, 1)));
		ASTClass.instrum("Expression Statement","49");
        assertEquals(12, Util.lastMonth(LocalDate.of(2014, 1, 31)));
		ASTClass.instrum("Expression Statement","50");
        assertEquals(1, Util.lastMonth(LocalDate.of(2014, 2, 1)));
		ASTClass.instrum("Expression Statement","51");
        assertEquals(11, Util.lastMonth(LocalDate.of(2014, 12, 31)));
		ASTClass.instrum("Expression Statement","of","LocalDate.of(2014,12,31)","52");
    }
}