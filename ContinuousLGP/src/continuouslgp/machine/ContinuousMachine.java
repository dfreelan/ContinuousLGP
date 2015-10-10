/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.Engine;
import continuouslgp.Engine.FloatMath;
import continuouslgp.program.ContinuousProgram;

/**
 *
 * @author dfreelan
 */
public class ContinuousMachine {
    boolean useGlobalRegisters = false;
    boolean resetPcs = false;
    int maxPCs;
    int curPcs = 0;
    public ProgramCounter[] pcs;
    RegisterProfile profile;
    public float[] registers;
    int execType = 0;
    public ContinuousProgram program;
    public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        profile.totalLinesOfCode = p.lines.length;
        this.execType = execType;
        this.profile = profile;
        //this.maxPCs = maxPCs;
        this.program = p;
        pcs = new ProgramCounter[profile.totalLinesOfCode];
        profile.length = numRegisters;
        pcs[0] = new ProgramCounter(0,1.0f,execType, profile);
        for(int i = 1; i<profile.totalLinesOfCode; i++){
            pcs[i] = new ProgramCounter(i,0.0f,execType, profile);
        }
        registers = new float[numRegisters];
        for(int i = 0; i<registers.length; i++){
            registers[i] = 1.0f;
        }
    }
    public void printProg(){
        program.printAll();
    }
    public void mutate(float strength){
        program.mutate(strength);
    }
    public void changeStrength(float strength){
        program.changeStrength(strength);
    }
    public void doStep(){
        ProgramCounter allCounters[][] = new ProgramCounter[pcs.length][];
        int i = 0;
        //System.err.println("pcs[0] weight " + pcs[0].weight);
        for(ProgramCounter pc: pcs){
            //System.out.println(pc.line);
            if(pc.weight!=0.0f){
                pc.registers = registers.clone();
                allCounters[i] =  pc.doInstruction(program.lines[pc.line]);
            
            }else{
                allCounters[i] = new ProgramCounter[0];
            }
            //pc.weight = 0;
            
            i++;
        }
        float[] oldRegisters = registers.clone();
        registers = new float[registers.length];
        
        float totalWeight = 0;
        for(int k = 0; k<pcs.length; k++){
            if(pcs[k].weight!=0.0f){
                totalWeight += pcs[k].weight;
                System.err.println(k + " line:"  + pcs[k].line + ", " + pcs[k].weight);
                FloatMath.printFloatArr(program.lines[pcs[k].line].getLineValues());
                for(int a = 0; a<registers.length; a++){
                    System.err.println("value of k,a is " + k + ", " + a +":" + pcs[k].registers[a]);
                    registers[a] += pcs[k].registers[a]*pcs[k].weight;
                }
                pcs[k].weight = pcs[k].updateWeight;
            }
         }
        System.err.println("totalWeight is " + totalWeight);
        // if(totalWeight<1.0f && totalWeight!=0.0f){
        //  
        //}
        //
        for(int k = 0; k<pcs.length; k++){
            totalWeight += pcs[k].weight;
            //System.err.println("BEFORE THE before line:"  + k + ", " + pcs[k].weight);
        }
        for(int k = pcs.length-1; k>0; k--){
            pcs[k].weight = pcs[k-1].weight;
            pcs[k].updateWeight = 0.0f;
        }
        pcs[0].weight = 0;
        pcs[0].updateWeight = 0;
         totalWeight = 0;
        for(int k = 0; k<pcs.length; k++){
            totalWeight += pcs[k].weight;
            //System.err.println("before line:"  + k + ", " + pcs[k].weight);
        }
        for(int k = 0; k<allCounters.length; k++){
            for(int a = 0; a<allCounters[k].length; a++){
                
                if(allCounters[k][a].line<pcs.length){
                    pcs[allCounters[k][a].line].weight+=allCounters[k][a].weight;
                    if(pcs[allCounters[k][a].line].weight>1.001f){
                        System.err.println("bad");
                        System.exit(0);
                    }
                }
            }
        }
        totalWeight = 0;
        for(int k = 0; k<pcs.length; k++){
            totalWeight += pcs[k].weight;
            System.err.println("after line:"  + k + ", " + pcs[k].weight);
        }
        
         System.err.println("total weight is  ACTUALY" + totalWeight);
         System.err.println("register value:" + registers[3]);
    }
    public void hardRestart(){
        pcs[0] = new ProgramCounter(0,1.0f,execType, profile);
        for(int i = 1; i<profile.totalLinesOfCode; i++){
            pcs[i] = new ProgramCounter(i,0.0f,execType, profile);
        }
        for(int i = 0; i<registers.length; i++){
            registers[i] = 1;
        }
    }
    public void softRestart(){
        if(resetPcs){
           curPcs = 1;
           pcs[0] = new ProgramCounter(0,1.0f,execType, profile);
           for(int i = 1; i<profile.totalLinesOfCode; i++){
               pcs[i] = new ProgramCounter(i,0.0f,execType, profile);
           }
           

        }
        for(int i = 0; i<registers.length; i++){
            registers[i] = 1;
        }
    }
}
