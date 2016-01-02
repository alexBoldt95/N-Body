import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;

import princeton.StdAudio;
import princeton.StdDraw;

public class NBody {
    public static final double G = 6.67E-11;
    
    /**
     * Displays file chooser for browsing in the project directory. and opens
     * the selected file
     *
     * @return a new Scanner that produces values scanned from the selected
     *         file. null if file could not be opened or was not selected
     */
    public static Scanner openFileFromDialog() {
    	
        Scanner scan = null;
        System.out.println("Opening file dialog.");
        JFileChooser openChooser = new JFileChooser(System.getProperties()
                                                    .getProperty("user.dir"));
        int retval = openChooser.showOpenDialog(null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = openChooser.getSelectedFile();
            try {
                scan = new Scanner(file);
                System.out.println("Opening: " + file.getName() + ".");
            }
            catch (FileNotFoundException e) {
                System.out.println("Could not open selected file.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File open canceled.");            
        }
        return scan;
    }

    /**
     * returns Euclidean distance between (x1, y1) and (x2, y2)
     *
     * @param x1
     *            x-coordinate of point 1
     * @param y1
     *            y-coordinate of point 1
     * @param x2
     *            x-coordinate of point 2
     * @param y2
     *            y-coordinate of point 2
     * @return Euclidean distance between (x1, y1) and (x2, y2)
     */
    public double distance(double x1, double y1, double x2, double y2) {         
        return Math.pow((Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2)), 0.5);
    }
    
    
    /**
     * return the magnitude of the gravitational force between two bodies of
     * mass m1 and m2 that are distance r apart
     *
     * @param m1
     *            mass of body 1 in kg
     * @param m2
     *            mass of body 2 in kg
     * @param r
     *            distance in m
     * @return force between m1 and m2 that are distance r apart
     */
    public double force(double m1, double m2, double r) {
        double F=(G*m1*m2)/(Math.pow(r, 2));
        return F;
    }


    /**
     * Returns the x positions and y positions of bodies
     * @param totalTime The total amount of universe time to run for
     * @param timeStep The value of delta t in the equations to calculate position
     * @param info The scanner with info about the initial conditions of the
     * bodies
     * @return an array whose first element is the x positions of the bodies,
     * and whose second element is the y positions of the bodies at time
     * t = totalTime
     */
    public double[][] positions(Scanner info, int totalTime, int timeStep) {
    	int N=info.nextInt();
    	
    	double[] px=new double[N];
    	double[] py=new double[N];
    	double[] vx=new double[N];
    	double[] vy=new double[N];
    	double[] mass=new double[N];
    	String[] image=new String[N];
    	
    	double[][] verse={px, py, vx, vy, mass}; //The verse will contain the starting information of the universe
    	double rUni=info.nextDouble();  //rUni is the radius of the universe
    	
    	int objLine=0;               //objLine will be used as a counter to keep track of which line of the universe
    	//file we are on
    	int dataPt=0;				//dataPt is the index of each point of initial data
    	while(objLine<N){
    		
    		if(info.hasNextDouble()){
    			verse[dataPt][objLine]=info.nextDouble();
    			dataPt++;
    		}
    		else{
    			image[objLine]=info.next();
    			objLine++;
    			dataPt=0;
    		}
    	}
    	info.close();
    	
//    	System.out.println(N);             //Testing code to make sure arrays are in order: looks good
//    	System.out.println(rUni);
//    	for(int i=0; i<5; i++){
//    	System.out.println(Arrays.toString(verse[i]));
//    	}
//    	System.out.println(Arrays.toString(image));
    	
    	StdDraw.setXscale(-rUni, rUni);    //Setting up the universe
    	StdDraw.setYscale(-rUni, rUni);
    	
//Starting the simulation loop
    	for(int t=0; t<totalTime; t+=timeStep){
    		StdDraw.picture(0, 0, "data/starfield.jpg");
    			for(int i=0; i<N; i++){
    				StdDraw.picture(px[i], py[i], "data/"+image[i]);
    				double[] Fx=new double[N];
    				double[] Fy=new double[N];
    				for(int j=0; j<N; j++){
    					double F;
    					double r=distance(px[i], py[i], px[j], py[j]);
    					if(i!=j){    						
    						F=force(mass[i], mass[j], r);
    						Fx[j]=F*((px[j]-px[i])/r);
        					Fy[j]=F*((py[j]-py[i])/r);
    					}
    					else{
    						Fx[j]=0;
    						Fy[j]=0;
    					}
    					
   					
    				}
    				double netFx=0;
    				double netFy=0;
    				for(double F:Fx){
    					netFx+=F;
    				}
    				for(double F:Fy){
    					netFy+=F;
    				}
    				double ax=netFx/mass[i];
    				double ay=netFy/mass[i];
    				vx[i]+=ax*timeStep;
    				vy[i]+=ay*timeStep;		
    				px[i]+=vx[i]*timeStep;
    				py[i]+=vy[i]*timeStep;
   		 		}
    		
    	StdDraw.show(30);		
    	}
    	
        double[][] output = new double[2][N];
        output[0]=px;
        output[1]=py;
        return output;
        }

        


    public static void main(String[] args) throws FileNotFoundException {
        Scanner info;
        int time, dt;
        if (args.length == 3){
            info = new Scanner(new File("data/"+args[0]));
            time = Integer.parseInt(args[1]);
            dt = Integer.parseInt(args[2]);
        }
        else{
            info = openFileFromDialog();
            time = 100000000;
            dt = 25000;
        }

        //StdAudio.play("data/2001.mid");
        NBody myNBody = new NBody();
        double[][] result=myNBody.positions(info, time, dt);
        System.out.println(Arrays.toString(result[0]));
        System.out.println(Arrays.toString(result[1]));
        StdDraw.clear();
        StdAudio.close();
    }
}

