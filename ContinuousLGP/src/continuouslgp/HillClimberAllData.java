/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp;

import static continuouslgp.ContinuousLGP.getOperators;
import continuouslgp.Engine.Engine;
import continuouslgp.machine.ContinuousMachine;
import continuouslgp.machine.RegisterProfile;
import continuouslgp.program.ContinuousProgram;
import java.util.Random;

/**
 *
 * @author dfreelan
 */
public class HillClimberAllData {
    static Random generator = new Random();
    public static void main(String[] args){
         ContinuousProgram prog = new ContinuousProgram(100,5,5,5,2,2,2);
        // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        Engine engine = new Engine(getOperators(),null);
        RegisterProfile prof = new RegisterProfile(engine);
        ContinuousMachine machine = new ContinuousMachine(prog,prof,1,5,0);
        int evaluations = 0;
        float[][] trainingData = getTrainingData(20);
        float[][] testingData = getTrainingData(20);
        float currentErr =  getTestingErr(machine,trainingData);
        while(Float.isInfinite(currentErr)){
            prog = new ContinuousProgram(10,5,5,5,0,0,0);
            machine = new ContinuousMachine(prog,prof,1,5,0);
            currentErr =  getTestingErr(machine,trainingData);
            System.err.println("err was infinite");
        }
        for(int iterations = 0; iterations<50000; iterations++){
          
            if(evaluations>=50000) break;
           
            float mutateIncrement = .001f;
            float mutateAmount = mutateIncrement;
            
            currentErr = getTestingErr(machine,trainingData); //getError(trainingData[pair], machine);

            
           
            
            machine.mutate(mutateAmount);
            float newErr = getTestingErr(machine,trainingData);
            boolean wasBetter = false;
            while(newErr<currentErr && mutateAmount+mutateIncrement<1.0f){
                currentErr = newErr;
                if(wasBetter ==false){
                    System.err.println("makin it better, 1 second...");
                }
                wasBetter = true;
                mutateAmount += mutateIncrement;
                machine.changeStrength(mutateAmount);
                newErr = getTestingErr(machine,trainingData);
            }
            machine.changeStrength(mutateAmount-mutateIncrement);
            
            if(wasBetter){
                float err1 = getTestingErr(machine,testingData);
                if(Float.isInfinite(err1)){
                    err1 = -1;
                }
                float err2 = getTestingErr(machine, trainingData);
                if(Float.isInfinite(err2)){
                    err2 = -1;
                }
                System.err.println(iterations + "," +evaluations +", " +(newErr/currentErr)+ ", " + mutateAmount + ", " + currentErr + ", " + err1 + ", "  + err2);
                        
            }

            
        }
        float sum = 0;
        for(int pair = 0; pair<trainingData.length; pair++){
            sum+= Math.sqrt(getError(testingData[pair],machine))/20;
        }
        machine.printProg();
        
        for(int i = 0; i<200; i++){
            float k = (float)(i+.01);
            float input = k*(9.0f/200.0f);
            System.err.println(input + " " + getOutput(machine,input));
        }
        //System.err.println(sum);
    }
    
    static float[][] getTrainingData(int number){
        float[][] data = new float[20][2];
        for(int i = 0; i<data.length; i++){
            data[i][0] = generator.nextFloat();
            data[i][1] = (float)Math.tan(data[i][0]);//data[i][0]*data[i][0]*data[i][0]*data[i][0] + data[i][0]*data[i][0]*data[i][0] + data[i][0]*data[i][0] + data[i][0];//Math.sqrt(data[i][0]);
        }
        return data;
    }
    static float getTestingErr(ContinuousMachine machine, float[][] testingData){
        float sum = 0;
        for(int pair = 0; pair<testingData.length; pair++){
            sum+= Math.sqrt(getError(testingData[pair],machine))/20;
        }
        return sum;
        
    }
    static float getError(float[] inputOutputPair, ContinuousMachine machine){
       machine.hardRestart();
       machine.pcs[0].registers[0] = inputOutputPair[0];
       for(int i = 0; i<machine.program.lines.length; i++){
            machine.doStep();
            machine.pcs[0].registers[0] = inputOutputPair[0];
       }
       float answer = machine.pcs[0].registers[3];
       
       return (answer-inputOutputPair[1])*(answer-inputOutputPair[1]);
    }
    static float getOutput(ContinuousMachine machine, float input){
       machine.hardRestart();
       machine.pcs[0].registers[0] = input;
       for(int i = 0; i<machine.program.lines.length; i++){
            machine.doStep();
            machine.pcs[0].registers[0] = input;
       }
       float answer = machine.pcs[0].registers[3];
       
       return answer;
    }
}
