import java.util.*;
import java.io.*;

class check
{
	static boolean k;
	String f[] = null;
	String cd;
	List<Integer> list=new ArrayList<Integer>();
	check()
	{
		k=true;
		cd = System.getProperty("user.dir");
        File folders = new File(cd);
        f = folders.list();
        adjustArray();
	}


	public static void main(String args[])
	{
		check ob = new check();
		while(k)
		{
			System.out.print("Command >");
			ob.readCommands(ob.terminal());
		}
	}


	void adjustArray()
	{
		String fl[] = new String[f.length-1];
		int i=0;
		for(String s : f)
		{
			if(s.equals("check.java"))
				continue;
			else
			{
				fl[i]=s;
				i++;
			}
		}
		f=fl.clone();
	}
	void getFiles()
	{
        File folders = new File(cd);
        f = folders.list();
        adjustArray();
	}
	void cmdCd(String s)
	{
		cd=s;
		getFiles();
	}
	void listFiles()
	{
		System.out.println("Files Found :");
        for (int i=0; i<f.length; i++)
        {
            
            System.out.println(f[i]);
        }
	}
	String terminal()
	{
		Scanner in = new Scanner(System.in);
		String inp=in.nextLine();
		return inp;
	}
	void readCommands(String str)
	{
		str=str.trim()+" ";
		int c=0;
		for(int i=0; i<str.length(); i++)
		{
			char ch=str.charAt(i);
			if(ch==' ')
				c++;
		}
		String arr[] = new String[c];
		int ss=0;
		c=0;
		for(int i=0; i<str.length(); i++)
		{
			char ch=str.charAt(i);
			if(ch==' ')
			{
				String s=str.substring(ss,i);
				ss=i+1;
				arr[c]=s;
				c++;
			}
		}
		/*for(String s:arr)
		{
			System.out.println(s);
		}*/
		checkCommands(arr[0],arr);
	}
	void checkCommands(String s, String arr[])
	{
		switch(s)
		{
			case "exit":
			exit();
			break;
			case "bye":
			exit();
			break;
			case "list":
			listFiles();
			break;
			case "run":
			if(arr[1].equals("~all"))
				loopExe();
			else
				runProg(arr[1]);
			break;	
			case "trml":
			getFiles();
			runProcess(arr);
			break;
			default :
			System.out.println("\t\tInvalid Command "+s);
		}
	}
	void exe(int n, int i)
    {
        switch(n)
        {
        	case 1:
        	exeJava(f[i]);
        	break;
        	case 2:
        	exeCpp(f[i]);
        	break;
        	case 3:
        	exePy(f[i]);
        	break;
        }
    }
    int runProg(String str)
    {
    	boolean k=true;
    	for(String s : f)
    	{
    		if(str.equals(s))
    		{
    			k=false;
    			break;
    		}
    	}
    	if(k)
    	{
    		System.out.println("\t\tFile Not Found");
    		return 0;
    	}
    	switch(getFileType(str))
    	{
    		case "java":
    		exeJava(str);
    		break;    		
    		case "cpp":
    		exeCpp(str);
    		break;
    		case "py":
    		exePy(str);
    		break;
    	}
		return 0;
    }
    void loopExe()
    {
    	int n=-1;
    	for(int i=0; i<f.length; i++)
        {
            if(f[i].equals("a.out"))
                continue;
            String ext = getFileType(f[i]);
            System.out.println("\t\t\tRunning "+f[i]);
            if(ext.equals("java"))
            	exe(1,i);
            if(ext.equals("cpp"))
            	exe(2,i);
            if(ext.equals("py"))
            	exe(3,i);
        }
    }

    //running commands in terminal
    void exeJava(String fn)
    {
        try
        {
            runProcess("java "+fn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
        System.out.println();
        System.out.println("***************");
        System.out.println();
    }
    void exeCpp(String fn)
    {
        try
        {
            runProcess("c++ "+fn);
            runProcess("./a.out");
            runProcess("rm -rf a.out");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("***************");
        System.out.println(); 
    }
    void exePy(String fn)
    {
        try
        {
            runProcess("python3 "+fn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
        System.out.println();
        System.out.println("***************");
        System.out.println();
    }


    String getFileType(String name)
    {
    	String rt=name.substring(name.lastIndexOf(".") + 1 , name.length());
    	return rt;
    }
	void changeDir(String str)
	{
		//Code to change directory of the program
	}
	void exit()
	{
		System.out.println("\t\tExitting Program !!");
		k=false;
	}
	private static void printLines(String cmd, InputStream ins) throws Exception
    {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) 
        {
            System.out.println(cmd + " " + line);
        }
    }
	private static void runProcess(String arr[])
	{
		String command="";
		for(int i=1; i<arr.length; i++)
		{
			command=command+" "+arr[i];
		}
		command=command.trim();
		try
		{
			Process pro = Runtime.getRuntime().exec(command);
	        printLines("",pro.getInputStream());
	        printLines("",pro.getErrorStream());
	        pro.waitFor();
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	}
	private static void runProcess(String command)
    {
        try
		{
			Process pro = Runtime.getRuntime().exec(command);
	        printLines("",pro.getInputStream());
	        printLines("",pro.getErrorStream());
	        pro.waitFor();
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
    }
    void addIgnoreList(String filename) 
    {  
    	String str="";
        int ch; 
        FileReader fr=null; 
        try
        { 
            fr = new FileReader("ignorelist.txt"); 
	        while ((ch=fr.read())!=-1) 
	            str=str+(char)ch; 
	        fr.close(); 
        } 
        catch (FileNotFoundException fe) 
        { 
            System.out.println("creating new ignore list");
            str="ignorelist.txt"; 
        }
        catch(IOException e)
        {
        	System.out.println(e);
        }
        str=str+";"+filename;
  		try
  		{
	        FileWriter fw=new FileWriter("ignorelist.txt"); 
	        for (int i = 0; i < str.length(); i++) 
	            fw.write(str.charAt(i)); 
	  
	        System.out.println(filename+" added to ignore list successfully");  
	        fw.close();
        }
        catch(Exception e)
        {
        	System.out.println("Failed : "+e);
        } 
    } 
}
