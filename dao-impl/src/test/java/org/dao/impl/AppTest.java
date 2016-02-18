package org.dao.impl;

import java.util.List;

import org.domain.MyFileSystem;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	MyFileSystem mf=new MyFileSystem();
    	mf.setName("First file");
    	mf.setType("dir");
    	mf.setParentFolder(0);
    	MyFileSystemImpl mfi=new MyFileSystemImpl();
    	mfi.create(mf);
    	List<MyFileSystem> mff=mfi.selectAll();
    	
    	for (MyFileSystem myFileSystem : mff) {
    		System.out.println(myFileSystem);
			
		}
    	System.out.println("-----------------------------------");
    	mff=mfi.selectAll();
    	for (MyFileSystem myFileSystem : mff) {
    		System.out.println(myFileSystem);
			
		}
    	
        assertTrue( true );
    }
}
