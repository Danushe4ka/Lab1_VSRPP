/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1;

/**
 *
 * @author User
 */
public class SimpleCalculator
{
    private double _sum(double chislo1, double chislo2)
    {
        double clc;
        clc = chislo1 + chislo2;
        return clc;
    }
    
    private double _mult(double chislo1, double chislo2)
    {
        double clc;
        clc = chislo1 * chislo2;
        return clc;
    }
    
    private double _delMult(double chislo1, double chislo2)
    {
        double clc;
        clc = chislo1 / chislo2;
        return clc;
    }
    
    private double _sqrt(double chislo)
    {
        double sNum;
        double clc = chislo/2;
        do
        {
            sNum = clc;
            clc = (sNum + (chislo / sNum)) / 2;
        }
        while((sNum - clc) != 0);
        return clc;
    }
    
    public double Calculate(double chislo1,double chislo2, char type)
    {
        double clc;
        clc = switch(type)
        {
            case '+' -> _sum(chislo1, chislo2);
            case '-' -> _sum(chislo1, -1.0 * chislo2);
            case '*' -> _mult(chislo1, chislo2);
            case '/' -> _delMult(chislo1, chislo2);
            default -> 0.0;
        };
        return clc;
    }
    public double Calculate(double chislo1, char type)
    {
        double clc;
        clc = switch (type) {
            case '^' -> _sqrt(chislo1);
            default -> 0.0;
        };
        return clc;
    }
}
