/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecture1;

/**
 *
 * @author yi
 */
public class Account {
    
    private String user;
    private String name;
    private String full_name;
    private String DOB;
    private String type;
    private String email;
    private String SSN;
    
    public Account(){}
    
    public void setUser(String user)
    {
        this.user = user;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setDOB(String DOB)
    {
        this.DOB = DOB;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setSSN(String SSN)
    {
        this.SSN = SSN;
    }
    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }
    
    
    public String getUser()
    {
        return user;
    }
    public String getName()
    {
        return name;
    }
    public String getDOB()
    {
        return DOB;
    }
    public String getType()
    {
        return type;
    }
    public String getEmail()
    {
        return email;
    }
    public String getSSN()
    {
        return SSN;
    }
    public String getFull_name()
    {
        return full_name;
    }
}