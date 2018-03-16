package org.openshift;

import java.io.*;
import java.util.*;
import java.lang.*;

//Call a shell script 
public class ExecScript 
{

public static void main(String[] args) 
	{
		//Call the runscript to test it works.
		runScript(args);
	}
	
	//public String generateInsult() {
	public static String runScript(String[] parms) 
	{
		System.out.println("Entering the runScript method");
		// String[] cmd = {"sh", "myScript.sh", "/Path/to/my/file"};
			
		final StringBuffer theOutput = new StringBuffer();
		try
		{
			// final Process aProcess = Runtime.getRuntime().exec("sh -c ls %s");
			final Process aProcess = Runtime.getRuntime().exec("dir");
			System.out.println("Called the script ");
			aProcess.waitFor();
			System.out.println("Returned from calling the script ");
			final BufferedReader aBufReader = new BufferedReader(new InputStreamReader(aProcess.getInputStream()));
			String aLine = "";
			while ((aLine = aBufReader.readLine()) != null)
			{
				theOutput.append(aLine + "\n");
				System.out.println("adding a line to the output buffer ");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Returning the response to the caller");
		if (theOutput != null)
		{
			System.out.println(theOutput.toString());
			return theOutput.toString();
		}
		else
			return null;
	}
}
