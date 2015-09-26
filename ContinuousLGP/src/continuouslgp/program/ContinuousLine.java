/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program;

import continuouslgp.program.representation.*;


/**
 *
 * @author dfreelan
 */
public class ContinuousLine {
    static int instrType = 0;
    static int srcType = 0;
    static int destType = 0;
    int length = 0;
    WeightVector instr;
    WeightVector src;
    WeightVector dest;
    
    public ContinuousLine(int i, int s, int d, int length, int srcLength, int destLength){
        this.length = length;
        instr = assignType(i,length);
        src = assignType(s,srcLength);
        dest = assignType(d,destLength);
    }
    public void mutate(float strength){
       instr.mutate(strength);
       src.mutate(strength);
       dest.mutate(strength);
    }
    public void changeStrength(float strength){
        instr.changeStrength(strength);
        src.changeStrength(strength);
        dest.changeStrength(strength);
    }
    private WeightVector assignType(int type, int length){
        switch(type){
            case 0:
                return new Simplex(length);
            case 1:
                return new Torus(length);
            case 2: 
                return new NaiiveSimplex(length);
            default:
                return null;
        }
    }

    public float[][] getLineValues() {
        float[][] ret = new float[3][];
        ret[0] = instr.getWeights();
        ret[1] = src.getWeights();
        ret[2] = dest.getWeights();
        return ret;
    }
}
