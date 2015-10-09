/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp;

import static continuouslgp.ContinuousLGP.getOperators;
import continuouslgp.Engine.Engine;
import continuouslgp.alu.controlflow.ControlFlow;
import continuouslgp.alu.controlflow.Goto;
import continuouslgp.alu.controlflow.IfLessSigmoidGeometric;
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
         ContinuousProgram prog = new ContinuousProgram(16,4,4,4,0,0,0);
        // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        Engine engine = new Engine(getOperators(),getControlFlow());
        RegisterProfile prof = new RegisterProfile(engine,prog.lines.length);
        ContinuousMachine machine = new ContinuousMachine(prog,prof,1,5,0);
        int evaluations = 0;
        float[][] trainingData = getTrainingData(20);
        float[][] testingData = getTrainingData(20);
        System.err.println("testing....");
        float currentErr =  getTestingErr(machine,trainingData);
        System.err.println("dont testing " + currentErr);
        while(Float.isInfinite(currentErr) || Float.isNaN(currentErr)){
            prog = new ContinuousProgram(16,4,4,4,0,0,0);
            machine = new ContinuousMachine(prog,prof,1,5,0);
            currentErr =  getTestingErr(machine,trainingData);
            System.err.println("err was infinite");
        }
         float mutateIncrement = .1f;
           
        for(int iterations = 0; iterations<10000; iterations++){
          
            if(evaluations>=50000) break;
            mutateIncrement = generator.nextFloat()*generator.nextFloat()*generator.nextFloat();
            float mutateAmount = mutateIncrement;
            currentErr = getTestingErr(machine,trainingData); //getError(trainingData[pair], machine);

            
           
            
            machine.mutate(mutateAmount);
            float newErr = getTestingErr(machine,trainingData);
            boolean wasBetter = false;
            int incrementAmount = 0;
            while(newErr<currentErr && mutateAmount+mutateIncrement<1.0f){
                currentErr = newErr;
                if(wasBetter ==false){
                    System.err.println("makin it better, 1 second...");
                }
                wasBetter = true;
                if(incrementAmount%100 == 99){
                    mutateIncrement*=10;
                }
                mutateAmount += mutateIncrement;
                
                machine.changeStrength(mutateAmount);
                newErr = getTestingErr(machine,trainingData);
                incrementAmount++;
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
                System.err.println(iterations + ", canhalt," +evaluations +", " +(newErr/currentErr)+ ", " + mutateAmount + ", " + currentErr + ", " + err1 + ", "  + err2);
                if(incrementAmount < 5 ){
                    mutateIncrement/=10;
                    if(mutateIncrement<.00000001f){
                        mutateIncrement = .00000001f;
                    }
                }else if(incrementAmount >20){
                    mutateIncrement*=10;
                }
            }else{
               // System.err.println("wasnt better");
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
            data[i][0] = generator.nextFloat()*5;
            data[i][1] = (float)Math.sqrt(data[i][0]) ;//data[i][0]*data[i][0]*data[i][0]*data[i][0] + data[i][0]*data[i][0]*data[i][0] + data[i][0]*data[i][0] + data[i][0];//Math.sqrt(data[i][0]);
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
       machine.registers[0] = inputOutputPair[0];
       for(int i = 0; i<machine.program.lines.length*2; i++){
            machine.doStep();
            machine.registers[0] = inputOutputPair[0];
            //System.err.println(machine.registers[2]);
       }
       float answer = machine.registers[2];
       
       return (answer-inputOutputPair[1])*(answer-inputOutputPair[1]);
    }
    static float getOutput(ContinuousMachine machine, float input){
       machine.hardRestart();
       machine.registers[0] = input;
       for(int i = 0; i<machine.program.lines.length*2; i++){
            machine.doStep();
            machine.registers[0] = input;
       }
       float answer = machine.registers[2];
       
       return answer;
    }
    static ControlFlow[] getControlFlow(){
        ControlFlow cfs[] = new ControlFlow[2];
        cfs[0] = new IfLessSigmoidGeometric();
        cfs[1] = new Goto();
        return cfs;
    }
}
