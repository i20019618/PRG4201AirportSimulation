/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation2;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author user
 */

public class Runway {
    
    private Semaphore semaphore = new Semaphore(1);
    private String runwayName;
    //private String currentPlane = null;
    private RunwayCallback callback;
    
    public Runway(String runwayName){
        this.runwayName = runwayName;
    }
    
    public interface RunwayCallback{
        void onOperationComplete(Airplane airplane);
    }
    
    public void setRunwayCallback(RunwayCallback callback){
        this.callback = callback;
    }
    
    public boolean isAvailable(){
        return semaphore.availablePermits()>0;
    }
    
    /*
    public String getRunwayName(){
        return runwayName;
    }
    
    public void printRunwayStatus(List<Runway> allRunways){
        for(Runway runway:allRunways){
            if(runway.semaphore.availablePermits()>0){
                System.out.println("[" + runway.runwayName + " available.]");
            }else{
                System.out.println("[" + runway.runwayName + " occupied by " + runway.currentPlane + "]");
            }
        }
    }
    */
    
    public void land(String planeName) throws InterruptedException{
        //currentPlane = planeName;
        semaphore.acquire();
        System.out.println(planeName + " is landing on " + runwayName + ".");
        Thread.sleep(15000); //simulate landing time
        System.out.println(planeName + " has landed on " + runwayName + ".");
        System.out.println(runwayName + " is now being cleaned/rested for 7 seconds.");
        Thread.sleep(7000); //rest time 7 sec after operation
        semaphore.release();
        System.out.println(runwayName + " is now available for next operation.");
        //currentPlane = null;
        //printRunwayStatus(allRunways);
        if(callback != null){
            callback.onOperationComplete(null);
        }
    }
    
        public void takeOff(String planeName) throws InterruptedException{
        //currentPlane = planeName;
        semaphore.acquire();
        System.out.println(planeName + " is taking off from " + runwayName + ".");
        Thread.sleep(8000); //simulate takeoff time
        System.out.println(planeName + " has taken off from " + runwayName + ".");
        System.out.println(runwayName + " is now being cleaned/rested for 7 seconds.");
        Thread.sleep(7000); //rest time 7 sec after operation
        semaphore.release();
        System.out.println(runwayName + " is now available for next operation.");
        //currentPlane = null;
        //printRunwayStatus(allRunways);
        if(callback != null){
            callback.onOperationComplete(null);
        }    
    }
}
