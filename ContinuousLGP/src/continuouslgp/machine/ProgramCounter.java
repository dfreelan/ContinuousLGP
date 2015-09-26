/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.Engine;
import continuouslgp.program.ContinuousLine;

/**
 *
 * @author dfreelan
 */
public class ProgramCounter {
    LineExecutor exec;
    int line;
    float weight;
    float[] registers;   
    RegisterProfile profile; 
    public ProgramCounter[] doInstruction(ContinuousLine line){
        return exec.doLine(line, registers);
    }

    public ProgramCounter(int line, float weight, int executorType, RegisterProfile profile){
        this.line = line;
     
        this.profile = profile;
        this.weight = weight;
        exec = getInstance(executorType);
    }
    public ProgramCounter(ProgramCounter other){
      this.line  = other.line;
      this.weight = other.weight;
      this.registers = other.registers.clone();
      this.exec = other.exec;
      this.profile = other.profile;
    }
    public ProgramCounter(ProgramCounter other, int line, float weight){
        this(other);
        this.line = line;
        this.weight = weight;
    }
    LineExecutor getInstance(int type){
        switch(type){
            case 0:
                return new CombinationalExecutor(profile,this);
            default:
                return null;
        }
    }
}
