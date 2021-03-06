/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

import continuouslgp.Engine.FloatMath;

/**
 *
 * @author dfreelan
 */
public class Simplex extends WeightVector {
    //every so many, renormalize the vector to make up for floating point errors. 
    // used in applyMutation
    final static int renormalizeTime = 25;
    int weightRenormalizeCounter = renormalizeTime;
    public Simplex(int length){
        super(length);
    }
    
    
    // called by superclass. required to be called, initizlize random individual
    @Override
    public void Initialize() {
       weights = getUniformSimplexSample();
       mutateVector = getUniformSimplexSample();
    }
    
    @Override
    public float[] mutate(float strength) {
        oldWeights = weights.clone();
        mutateVector = getUniformSimplexSample();
        applyMutation(strength);
        return weights;
    }

    @Override
    public float[] changeStrength(float strength) {
        weights = oldWeights.clone();
        applyMutation(strength);
        return weights;
    }

    /*
        using code from
        http://stackoverflow.com/questions/2171074/generating-a-probability-distribution
    */
    float[] getUniformSimplexSample(){
        float weights[] = new float[length];
        float sum = 0;
        
        for (int i = 0; i < length; i++)
        {
           weights[i] = 1.0f - generator.nextFloat();
           weights[i] = -1.0f * (float)Math.log(weights[i]);
           sum += weights[i];
        }
        for (int i = 0; i < length; i++)
        {
           weights[i] /= sum;
        }
        
        return weights;
    }
    /* 
        TODO: think about this function more... but I THINK this is what I want.
    */
    public void applyMutation(float strength) {
        //applies the mutation part
        for(int i = 0; i<weights.length; i++){
            //subtracting center of the simplex from the mutation vector.
            weights[i] = weights[i] + (mutateVector[i]- (1.0f/((float)length)))*(strength);
            //cant have a negative instruction, 
            //the actionless if is a paranoia of wanting the most common 
            //if statement to evaluate to true, since i read that in an 
            //article that evaluating to something consistently boosts the speed,
            //due to pipelining and all.
            
            if(weights[i]>0){
                
            }else{
                weights[i] = 0;
            }
             if(weights[i]<0.0f){
                System.err.println("weights were bad2");
                System.exit(0);
             }
        }
        //checks to see if we're due for some renormalizing due to potential floating point err
        
         FloatMath.normalize(weights);
       
    }

    @Override
    public float[] mutate(float strength, float strength2) {
        oldWeights = weights.clone();
        float realStr1 = strength/(strength+strength2);
        float realStr2 = strength/(strength+strength2);
        
        mutateComponent1 = getUniformSimplexSample();
        mutateComponent2 = getUniformSimplexSample();
        for(int i = 0; i<mutateVector.length; i++){
            mutateVector[i] = mutateComponent1[i]*strength + mutateComponent2[i]*strength2;
        }
        applyMutation(strength+strength2);
        return weights;
    }

    @Override
    public float[] changeStrength(float strength, float strength2) {
        weights = oldWeights.clone();
        float realStr1 = strength/(strength+strength2);
        float realStr2 = strength/(strength+strength2);
        for(int i = 0; i<mutateVector.length; i++){
            mutateVector[i] = mutateComponent1[i]*realStr1 + mutateComponent2[i]*realStr2;
        }
        applyMutation(strength+strength2);
        return weights;
    }
    
    
}
