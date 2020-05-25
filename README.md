outputoutput# infection

Uses a simple SIR compartmental model to predict the progress of an infection in a set of populations. 


## Build

Drop this Maven project into Eclipse and build

## Run

java -cp ${CLASSPATH} com.rsmaxwell.infection.app.App -c {filename}

Where {filename} is a configuration file in json format. 

A population is constructed from each group, and the SIR model used to predict the progress of an infection. 
Output is generated for each population and also a combined output for the total population named `All`.
The output is formated as requested in the `output` section of the configuration.  

Simple configuration:

``` json
{
   "maxTime":20,
   "groups":{
      "1":{
         "name":"everyone",
         "recovery":0.23,
         "population":10,
         "iStart":0.01
      }
   },
   "connectors":{
      "1.1":{
         "transmission":3.2
      }
   },
   "output":{
      "format": "jpeg",
      "filter": [ "All" ],
      "width": 800,
      "height": 400
   }
}
```

`maxTime` is used to set how long the simulation should run for in arbitrary time units. 

### groups

A group defines the initial state of a population. The group ID must be alphanumeric.

`name` is the display name for the group

`recovery` is constant related to how long an individual takes to recover after being infected.

`iStart` is used to set a populations initial infected value.

`rStart` is an optional field (default value 0) and is used to set a populations initial recovered value.

A populations initial susceptible value is calculated from: `1.0 - iStart - rStart`


### connectors

A connection represents properties related to a pair of groups. In particular how easily an infection passes from 
one population to another. 

If there is no connector provided from one group to another, then a default connection must be provided.
 
The ID of the connection is composed of the 2 group IDs separated by a dot.
   
The name of the default connection is the special name `default`

`transmission` is a constant related to how easily an infection spreads from one population to another

`transmissionEval` is a groovy expression which evaluates to the transmission constant 

`transmissionScript` is the name of a groovy script located under the `./scripts/` directory, which returns the transmission constant 

Only one of `transmission`, `transmissionEval`, `transmissionScript` should be specified.


### output

The `output` section defines how these results are formatted to generate the stream which is returned to the user. 
The `format` field may be one of the following values:

| format  | Type of stream | contents of stream                                       | parameters                                   | 
|---------|----------------|----------------------------------------------------------|----------------------------------------------|
| jpeg    | binary         | single results graph as a jpeg image                     | String[] filter, int width, int height       |
| png     | binary         | single results graph as a png image                      | String[] filter, int width, int height       |
| svg     | binary         | single results graph as an svg image                     | String[] filter, int width, int height       |
| jpegzip | binary         | multiple results graphs as a zip containing jpeg images  | String[] filter, int width, int height       |
| pngzip  | binary         | multiple results graphs as a zip containing png images   | String[] filter, int width, int height       |
| svgzip  | binary         | multiple results graphs as a zip containing svg images   | String[] filter, int width, int height       |
| json    | character      | raw data in json format                                  | String[] filter                              |
| text    | character      | raw data in text format                                  | String[] filter                              |

The `output.filter` parameter allows a subset of the results to be output. The format is simply a list of the population group names and/or the 
special  population `All`.

The `width` and `height` parameters define the size of the resulting image


## Example configuration with 2 populations:

``` json
{
   "maxTime":20,
   "groups":{
      "1":{
         "name":"young",
         "recovery":0.23,
         "population": 10,
         "iStart":0.01
      },
      "2":{
         "name":"old",
         "recovery":0.23,
         "population": 10,
         "iStart":0
      }
   },
   "connectors":{
      "1.1":{
         "transmission":3.2
      },
      "1.2":{
         "transmission":0.31
      },
      "2.1":{
         "transmission":0.31
      },
      "2.2":{
         "transmission":3.2
      }
   },
   "output":{
      "format": "jpeg",
      "filter": [ "All" ],
      "width": 800,
      "height": 400
   }
}
```

However in the real world, the transmission value changes with time (e.g. before and after lockdown). One way to model this
is to provide the transmission as a function which is evaluated at each time step.    

Configuration where the transmission is provided by a evaluating a `groovy` string


``` json
{
   "maxTime":20,
   "groups":{
      "1":{
         "name":"young",
         "recovery":0.23,
         "population": 10,
         "iStart":0.01
      },
      "2":{
         "name":"old",
         "recovery":0.23,
         "population": 10,
         "iStart":0
      }
   },
   "connectors":{
      "1.1":{
         "transmissionEval": "if (t < 1) 3.2 else 0.31"
      },
      "1.2":{
         "transmission":0.31
      },
      "2.1":{
         "transmission":0.31
      },
      "2.2":{
         "transmissionEval": "if (t < 1) 3.2 else 0.31"
      }
   },
   "output":{
      "format": "jpeg",
      "filter": [ "All" ],
      "width": 800,
      "height": 400
   }
}
```

Whilst the transmission evaluation strings as above are simple in their nature, they are compiled on the fly and 
evaluated every time step, and so do not perform well. 

A groovy script stored as a file can be compiled by groovy once and the object code evaluated each
time step, and so perform better. This requires the groovy file to have been provided before the modelling run.
     
The groovy file is expected to be in the `./scripts` directory and the filename must be alphanumeric. 

The groovy script is provided with the following arguments:

    * double time
    * String group1
    * String group2   

... and is expected to return a 'double'

An example groovy script located at `./scripts/foo.groovy`

``` groovy

if (group1 != group2) {
   return 0.31
}

if (time < 1) {
   return 3.2;
}

return 0.31;
```

A configuration referencing the above script file. 

``` json
{
   "maxTime":20,
   "groups":{
      "1":{
         "name":"young",
         "recovery":0.23,
         "population":10,
         "iStart":0.01
      },
      "2":{
         "name":"old",
         "recovery":0.23,
         "population":10,
         "iStart":0
      }
   },
   "connectors":{
      "default":{
         "transmissionScript":"foo"
      }
   },
   "output":{
      "format": "jpeg",
      "filter": [ "All" ],
      "width": 800,
      "height": 400
   } 
}

```

