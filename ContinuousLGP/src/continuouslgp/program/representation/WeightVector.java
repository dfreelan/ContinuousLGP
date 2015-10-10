/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

import java.util.Random;

/**
 *
 * @author dfreelan
 */
public abstract class WeightVector {
    static Random generator = new Random();
    
    int length = 0;
    
    float[] weights = null;
    float[] oldWeights = null;
    float[] mutateVector = null;
    
    float[] mutateComponent1 = null;
    float[] mutateComponent2 = null;
    
    public WeightVector(int length){
        this.length = length;
        Initialize();
    }
    
    
    //should actually give weights values, in some random fasion.
    abstract void Initialize();
    
    public abstract float[] mutate(float strength);
    public abstract float[] changeStrength(float strength);
    
    public abstract float[] mutate(float strength,float strength2);
    public abstract float[] changeStrength(float strength,float strength2);
    
    public float[] getWeights(){
        return weights;
    }
    void setWeights(float[] weights){
        this.weights = weights;
    }


}
