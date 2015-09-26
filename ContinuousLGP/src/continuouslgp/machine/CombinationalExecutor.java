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
        commandWrapper res1 = generateCommands(line.getLineValues());
        ProgramCounter[] pcs = new ProgramCounter[1];
        System.out.println("this is where i'm calling the engine");
        float[] result = profile.engine.getResult(res1.questions, registers, res1.weights);
        pcs[0]= counter;
        counter.registers = result;
        return pcs;
    }

    private commandWrapper generateCommands(float[][] instructions) {
        commandWrapper wrap = new commandWrapper();
        int[][] questions = new int[instructions[0].length*instructions[1].length][3];
        float weights[] = new float[questions.length];
        wrap.questions = questions;
        wrap.weights = weights;
        int count = 0;
        System.out.println("instruction:");
        FloatMath.printFloatArr(instructions[0]);
        System.out.println("src:");
        FloatMath.printFloatArr(instructions[0]);
        System.out.println("dest:");
        FloatMath.printFloatArr(instructions[0]);
        for(int i = 0; i<instructions[0].length; i++){
            int type = i;
            for(int a = 0; a<instructions[1].length; a++){
                int src = a;
                if(instructions[1][a] != 0.0f)
                for(int k = 0; k <instructions[2].length; k++){
                    int dest = k;
                    if(instructions[2][k]!=0){
                        questions[count][0] = type;
                        questions[count][1] = src;
                        questions[count][2] = dest;
                        weights[count] = instructions[0][i]*instructions[1][a]*instructions[2][k];
                    }
                }
            }
        }
        return wrap;
    }

    
}
class commandWrapper{
    int[][] questions;
    float[] weights;
}