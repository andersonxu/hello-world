package testing.uphill;


public class Threadtext {
	
	    static int i = 0;

	    public static void main(String args[]) throws Exception
	    {
	        int time = 0;
	        
		    System.out.println("Thread text");
		    
	        Thread t = new Thread() {
	        	public void run() {
	        		try
	                {
	                    while (!isInterrupted())
	                    {
	                        i = i + 1;

	                        Thread.sleep(1000);
	                    }
	                }
	                catch (InterruptedException e) {
	                	//System.out.println(e.message);
	                	}
	                catch (Exception e) {
	                	//System.out.println(e.toString());
	                	}
	        	}
	        };

	        t.start();

	        while (time < 20)
	        {
	            System.out.println("i=" + i);

	            Thread.sleep(500);
	            time +=1;
	        }

	        t.interrupt();

	        System.out.println("main thread done");
	    }

	    
}
