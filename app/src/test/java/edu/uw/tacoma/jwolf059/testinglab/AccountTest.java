package edu.uw.tacoma.jwolf059.testinglab;


import org.junit.Test;

import edu.uw.tacoma.jwolf059.testinglab.autheticate.Account;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by jwolf on 11/15/2016.
 */

public class AccountTest {



    Account testAcct = new Account("TestEmail@uw.edu", "testpas$word12");
    Account testAcct2 = new Account("TestEmail@uw.edu", "testpas$word12");

    @Test
    public void testAccountConstructor() {
        assertNotNull(new Account("mmuppa@uw.edu", "test1@3"));
    }

    @Test
    public void testAccountConstructorBadEmail() {
            Account testa = new Account("mmuppauw.edu", "test1@3");
            assertNull(testa.getmEmail());
    }

    @Test
    public void testAccountConstructorBadPassword() {
        Account testa = new Account("mmuppauw.edu", "test1@3");
        assertNull(testa.getmPassword());
    }


    @Test
    public void testIsVaildEmailNullEmail() {
       assertFalse(testAcct.isValidEmail(null));
    }

    @Test
    public void testIsVaildEmailVaildEmail() {
        assertTrue(testAcct.isValidEmail("testuser@gmail.com"));
    }


    @Test
    public void testIsVaildEmailInvaildEmail() {
        assertFalse(testAcct.isValidEmail("testEmail.uw.edu"));
    }


    @Test
    public void testIsVaildPasswordVaildPassword() {
        assertTrue(testAcct.isValidPassword("te$t12"));
    }

    @Test
    public void testIsVaildPasswordNullPassword() {
        assertFalse(testAcct.isValidPassword(null));
    }

    @Test
    public void testIsVaildPasswordPasswordwithNoNumber() {
        assertFalse(testAcct.isValidPassword("Test@up"));
    }

    @Test
    public void testIsVaildPasswordPasswordwithNoSpecialChar() {
        assertFalse(testAcct.isValidPassword("Test1up"));
    }

    @Test
    public void testIsVaildPasswordPasswordtoShort() {
        assertFalse(testAcct.isValidPassword("te$t1"));
    }

    @Test
    public void testgetEmail() {
        assertEquals("TestEmail@uw.edu", testAcct2.getmEmail());
    }

    @Test
    public void testgetPassword() {
        assertEquals("testpas$word12", testAcct2.getmPassword());
    }



}
