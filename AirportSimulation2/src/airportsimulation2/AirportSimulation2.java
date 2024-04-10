/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package airportsimulation2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
/**
 *
 * @author user
 */
public class AirportSimulation2 implements Runway.RunwayCallback {

    //queues for landing and takeoff
    private static Queue<Airplane> landingQueue = new LinkedList<>();
    private static Queue<Airplane> takeoffQueue = new LinkedList<>();
    private Runway runway1, runway2, emergencyRunway;
    
    public AirportSimulation2(){
        runway1 = new Runway("Runway 1");
        runway2 = new Runway("Runway 2");
        emergencyRunway = new Runway("Emergency Runway");
        
        runway1.setRunwayCallback(this);
        runway2.setRunwayCallback(this);
        emergencyRunway.setRunwayCallback(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        // TODO code application logic here
        Runway runway1 = new Runway("Runway 1");
        Runway runway2 = new Runway("Runway 2");
        Runway emergencyRunway = new Runway("Emergency Runway");
        //List<Runway> allRunways = Arrays.asList(runway1, runway2, emergencyRunway);
        AirportSimulation2 simulation = new AirportSimulation2();
        
        runway1.setRunwayCallback(simulation);
        runway2.setRunwayCallback(simulation);
        emergencyRunway.setRunwayCallback(simulation);
        */
        AirportSimulation2 simulation = new AirportSimulation2();
        Random random = new Random();
        
        for(int i=1; i<=30; i++){
            String planeName = "Plane " + (char)(random.nextInt(26)+'A')+(random.nextInt(900)+100);
            String operation = random.nextBoolean()?"landing":"takeoff";
            //Runway assignedRunway = (operation.equals("landing")||random.nextInt(100)<5)?emergencyRunway:(random.nextBoolean()?runway1:runway2);
            Airplane plane = new Airplane(simulation.assignRunway(random), planeName, operation);
            
            if(operation.equals("landing")){
                landingQueue.offer(plane);
            }else{
                takeoffQueue.offer(plane);
            }
            
            simulation.processNextPlane();
            
            try{
                Thread.sleep(random.nextInt(5000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
        
        /*
        for(int i=1; i<=30; i++){
            String planeName = "Plane " + (char)(random.nextInt(26)+ 'A') + (random.nextInt(900) + 100);
            String operation = random.nextBoolean() ? "landing":"takeoff";
            
            Runway assignedRunway;
            if(random.nextInt(100)<5){//5% chance of emergency
                assignedRunway = emergencyRunway;
                operation = "emergency";
            }else{
                assignedRunway = random.nextBoolean()? runway1:runway2;
            }
            
            Airplane plane = new Airplane(assignedRunway, planeName, operation, allRunways);
            //plane.start();
            
            //add plane to appropriate queue
            if(operation.equals("landing")||operation.equals("emergency")){
                landingQueue.offer(plane);
            }else{
                takeoffQueue.offer(plane);
            }
            
            //start the first plane in each queue if available FCFS
            if(!landingQueue.isEmpty() && landingQueue.peek().getState() == Thread.State.NEW){
                landingQueue.poll().start();
            }
            if(!takeoffQueue.isEmpty() && takeoffQueue.peek().getState() == Thread.State.NEW){
                takeoffQueue.poll().start();
            }
            
            //print current queue status
            printQueueStatus();
            
            try{
                Thread.sleep(random.nextInt(500));//control the next flight gap duration
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
        */
    }
    
        private Runway assignRunway(Random random){
            if(random.nextInt(100)<5){//5% chance of emergency landing on emergency runway
                return emergencyRunway;
            }
            return random.nextBoolean()?runway1:runway2;
        }
    
    @Override
    public void onOperationComplete(Airplane airplane){
        processNextPlane();
    }
    
    private synchronized void processNextPlane(){
        //Airplane nextPlane=null;
        if(!landingQueue.isEmpty() && runway1.isAvailable()){
            //landingQueue.poll().start();
            Airplane airplane = landingQueue.poll();
            airplane.start();
        }else if(!takeoffQueue.isEmpty() && runway2.isAvailable()){
            //takeoffQueue.poll().start();
            Airplane airplane = takeoffQueue.poll();
            airplane.start();
        }
        
        /*
        if(nextPlane != null){
            nextPlane.start();
        }
        */
        
        printQueueStatus();
    }
    
    private static void printQueueStatus(){
        System.out.println("Landing Queue: " + queueToString(landingQueue));
        System.out.println("Takeoff Queue: " + queueToString(takeoffQueue));
    }
    
    private static String queueToString(Queue<Airplane> queue){
        //List<String> planeNames = new ArrayList<>();
        StringBuilder builder=new StringBuilder();
        for(Airplane plane:queue){
            //planeNames.add(plane.getName());
            builder.append(plane.getName()).append(" (").append(plane.getOperation()).append("), ");
        }
        //return String.join(", ", planeNames);
        return builder.length()>0? builder.substring(0, builder.length()-2):"Empty";
    }
    
}
