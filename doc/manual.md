# infection

## Configuration

### configdir/config.json
 
```json
{
   "maxTime":20,
   "resolution":10,
   "integrationMethod": "com.rsmaxwell.infection.integrate.RungeKutta",
   "outputMethod": "com.rsmaxwell.infection.output.Graph"
}
```
 
- **maxTime**: units of time 
   
- **resolution**: the number of calculation steps per unit of time
   
- **integrationMethod**: The name of the class which implements the numerical integration method.
   - "com.rsmaxwell.infection.integrate.Euler"
   - "com.rsmaxwell.infection.integrate.RungeKutta"
      
- **outputMethod**: The name of the class which implements the output method
   - "com.rsmaxwell.infection.output.Print"
   - "com.rsmaxwell.infection.output.Graph"


### **/groups/ID**

   A directory with the name of a group identifier. Must be alphnumeric characters

### **/groups/ID/config.json**
```json
{
   "name":"young",
   "recovery":0.23,
   "population": 10,
   "iStart":0.01
}
```
   - **name**: descriptive name of the group   
   
   - **recovery**: A constant related to how quickly the population recovers from infection  
   
   - **population**: A number which indicated the size of this population compared to other populations
     	   
   - **iStart**: The initial fraction of the population which are infected

### **/conectors/ID.ID**
   A directory with the name of a "group identifier"."group identifier". There must be a connector for each pair of groups
   
### **/connectors/ID.ID/config.json**
```json
{
   "transmission":3.2
}
```
   - **transmission**: A constant related to the transmission of infection in this group   



