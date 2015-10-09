/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.machine;

import continuouslgp.Engine.FloatMath;
import continuouslgp.program.ContinuousLine;

/**
 *
 * @author dfreelan
 */
public class CombinationalExecutor extends LineExecutor {

    public CombinationalExecutor(RegisterProfile profile, ProgramCounter counter){
        super(profile,counter);
    }
    @Override
    ProgramCounter[] doLine(ContinuousLine line, float[] registers) {
       
        commandWrapper res2 = generateCommandsControl(line.getLineValues());
        ProgramCounter[] resultControl = profile.engine.getResultControl(res2.questions, registers, res2.weights,counter);
        
        
        
        commandWrapper res1 = generateCommands(line.getLineValues());
       
        float[] result = profile.engine.getResult(res1.questions, registers, res1.weights);
        counter.registers = result;
        //System.out.println("this is where i'm calling the engine");
        
        
        
        return resultControl;
    }
     private commandWrapper generateCommandsControl(float[][] instructions) {
        //TODO: need to add a noop with the weight of all the control flow operators.
        commandWrapper wrap = new commandWrapper();
        int numOperators = instructions[0].length-profile.engine.controlFlows.length;
        int[][] questions = new int[instructions[1].length*instructions[2].length*(instructions[0].length-numOperators)][3];
        //System.err.println((instructions[0].length-numOperators)+ " " + instructions[1].length + " " + instructions[2].length);
        //System.err.println("helo");
        float weights[] = new float[questions.length];
        wrap.questions = questions;
        wrap.weights = weights;
        int count = 0;
        float testWeight = 0.0f;
        
        for(int i = numOperators; i<instructions[0].length; i++){
            int type = i-numOperators;
            testWeight += instructions[0][i];
            //if(instructions[0][i] != 0.0f)
           
            for(int a = 0; a<instructions[1].length; a++){
                int src = a;
               // if(instructions[1][a] != 0.0f)
                for(int k = 0; k <instructions[2].length; k++){
                    int dest = k;
                    //System.out.println("found one! Question,weight " + type + " " + src + " " + dest + " " + (instructions[0][i]*instructions[1][a]*instructions[2][k]));
                   // if(instructions[2][k]!=0){
                        questions[count][0] = type;
                        questions[count][1] = src;
                        questions[count][2] = dest;
                        
                        weights[count] = instructions[0][i]*instructions[1][a]*instructions[2][k];
                        count++;
                    //}
                }
            }
        }
        System.err.println("weight accumlated should be about " + testWeight);
        return wrap;
    }
    private commandWrapper generateCommands(float[][] instructions) {
        //TODO: need to add a noop with the weight of all the control flow operators.
        commandWrapper wrap = new commandWrapper();
        int[][] questions = new int[(instructions[0].length-profile.engine.controlFlows.length)*instructions[1].length*instructions[2].length][3];
        float weights[] = new float[questions.length];
        wrap.questions = questions;
        wrap.weights = weights;
        float commandWeightSum = 0;
        int count = 0;
        float testWeight = 0.0f;
        /*System.out.println("instruction:");
        FloatMath.printFloatArr(instructions[0]);
        System.out.println("src:");
        FloatMath.printFloatArr(instructions[1]);
        System.out.println("dest:");
        FloatMath.printFloatArr(instructions[2]);*/
        for(int i = 0; i<instructions[0].length-profile.engine.controlFlows.length; i++){
            int type = i;
             testWeight += instructions[0][i];
            //if(instructions[0][i] != 0.0f)
            for(int a = 0; a<instructions[1].length; a++){
                int src = a;
                //if(instructions[1][a] != 0.0f)
                for(int k = 0; k <instructions[2].length; k++){
                    int dest = k;
                    //System.out.println("found one! Question,weight " + type + " " + src + " " + dest + " " + (instructions[0][i]*instructions[1][a]*instructions[2][k]));
                    //if(instructions[2][k]!=0){
                        questions[count][0] = type;
                        questions[count][1] = src;
                        questions[count][2] = dest;
                        
                        weights[count] = instructions[0][i]*instructions[1][a]*instructions[2][k];
                        
                        count++;
                    //}
                }
            }
        }
        //System.err.println("weight accumlated should be about " + testWeight);
        return wrap;
    }

    
}
class commandWrapper{
    int[][] questions;
    float[] weights;
}