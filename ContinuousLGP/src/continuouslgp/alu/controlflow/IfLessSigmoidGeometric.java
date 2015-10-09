/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp.alu.controlflow;

import continuouslgp.machine.ProgramCounter;

/**
 *
 * @author dfreelan
 */
public class IfLessSigmoidGeometric implements ControlFlow {

    @Override
    public ProgramCounter[] doControlFlow(ProgramCounter current, float[] registers, int src, int dest) {
        float line1Proportion = 0.0f;
        float line2Proportion = 0.0f;
        //calculating the proportion greater one is over the other.
        float divByZeroProtector = Math.abs(registers[dest]);
        if(divByZeroProtector<.00001f){
            divByZeroProtector = .00001f;
        }
        float result = (registers[src]-registers[dest])/divByZeroProtector;
        line1Proportion = sigmoid(result);
        line2Proportion = 1.0f-line1Proportion;
        if(line1Proportion>1.0f || line1Proportion <0.0f || line1Proportion+line2Proportion>1.0f){
            System.err.println("really bad");
            System.exit(0);
        }
        ProgramCounter newPCs[] = new ProgramCounter[2];
        newPCs[0] = new ProgramCounter(current);
        newPCs[0].weight=line1Proportion;
        newPCs[0].line++;
        
        newPCs[1] = new ProgramCounter(current);
        newPCs[1].weight=line2Proportion;
        newPCs[1].line+=2;
        
        
        return newPCs;
        //x/(x+e^(-x+1))
       
        
    }
    private float sigmoid(float input){
        return 1.0f/(1.0f+(float)Math.exp(-1.0f*input));
    }
    
}
