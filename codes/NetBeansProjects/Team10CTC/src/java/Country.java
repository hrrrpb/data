
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yi
 */


@Named(value = "country")
@Dependent
public class Country implements Serializable {
    String name;
    Integer rank;
    Double population;
    Double lifeExpectancy;
    
    public Country(){
        
    }
    
    public Country(String name, Integer rank, Double population, Double lifeExpectancy){
        this.name = name;
        this.rank = rank;
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
    }
    
    
    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    
    
    
    
}
