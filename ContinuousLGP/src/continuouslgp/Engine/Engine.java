/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.Engine;

import continuouslgp.alu.controlflow.ControlFlow;
import continuouslgp.alu.operator.Operator;
import continuouslgp.machine.ProgramCounter;

/**
 *
 * @author dfreelan
 */
public class Engine {
    Operator operators[];
    public ControlFlow controlFlows[];
    
    public Engine(Operator[] operators, ControlFlow[] controlFlow){
        this.operators = operators;
        this.controlFlows = controlFlow;
       
    }
    public ProgramCounter[] getResultControl(int[][] questions, float[] pseudoRegisters, float[] weights, ProgramCounter current){
     
        ProgramCounter[] dummyCounters;
        ProgramCounter[] cumulationCounters = new ProgramCounter[current.profile.totalLinesOfCode];
        //let's set up our cumulation counters.
        float commandWeightSum= 0.0f;
        
        for(int i = 0; i< current.profile.totalLinesOfCode; i++){
            cumulationCounters[i] = new ProgramCounter(current);
            cumulationCounters[i].line = i;
            cumulationCounters[i].weight = 0.0f;
        }
        
        int i = 0;
        while(i<questions.length){
            
            
            int controlFlow = questions[i][0];
            int src = questions[i][1];
            int dest = questions[i][2];
            //System.out.println("Operator, src, dest:" + controlFlow +"," + src+ "," + dest +  "weight:" + weights[i]);
            //FloatMath.printFloatArr( dummyRegisters);
            //ProgramCounter current, float[] registers, int src, int dest);
            
            dummyCounters = controlFlows[controlFlow].doControlFlow(current,pseudoRegisters, src, dest);

            for(int a = 0; a<dummyCounters.length; a++){
                 if(dummyCounters[a].line < cumulationCounters.length)
                    cumulationCounters[dummyCounters[a].line].weight += current.weight*weights[i]*dummyCounters[a].weight;
                 
                 commandWeightSum+= current.weight*weights[i]*dummyCounters[a].weight;
            }
            i++;
        }
       // System.err.println("combined weight of newly spawned PC's from this PC:" + current.line  + ", " + commandWeightSum);
        current.updateWeight = (current.weight-commandWeightSum);
        //System.err.println("here's the current weight, proposed newWeight  " + current.line + ":" +  current.weight+"," + current.updateWeight);
        /*for(int k = 0; k<cumulationCounters.length; k++){
            
            System.err.println("SUPER BEFORE THE before line:"  + cumulationCounters[k].line + ", " + cumulationCounters[k].weight);
        }*/
        return cumulationCounters;
    }
    public float[] getResult(int[][] questions, float[] pseudoRegisters, float[] weights){
        float[] cumulationRegisters = new float[pseudoRegisters.length];
        float[] dummyRegisters;
        float weightAccumulator= 0.0f;
        int i = 0;
        while(i<questions.length ){
            
            dummyRegisters = pseudoRegisters.clone();
            int operator = questions[i][0];
            int src = questions[i][1];
            int dest = questions[i][2];
            //System.out.println("Operator, src, dest:" + operator +"," + src+ "," + dest +  "weight:" + weights[i]);
            //FloatMath.printFloatArr( dummyRegisters);
            
            
            dummyRegisters = operators[operator].doOperation(dummyRegisters, src, dest);
            //System.out.println("result:");
            //FloatMath.printFloatArr(dummyRegisters);
            weightAccumulator += weights[i];
            for(int a = 0; a<cumulationRegisters.length; a++){
                
                if(dummyRegisters[a]!=Float.NaN && dummyRegisters[a]!=Float.NEGATIVE_INFINITY && dummyRegisters[a]!=Float.POSITIVE_INFINITY){
                    cumulationRegisters[a] += weights[i] * dummyRegisters[a];
                }
            }
            i++;
        }
        //System.err.println("weight accumulator:" + weightAccumulator);
        for(int a = 0; a<cumulationRegisters.length; a++){
            cumulationRegisters[a] += (1-weightAccumulator)*pseudoRegisters[a]; 
        }
        return cumulationRegisters;
    }
}
