# sql_exercise

### Usage and Dependencies.

You'll need to have the following languages/APIs/dependencies installed:

  * Ruby > v2.2
  * Python 2.7+ or 3.2+
  * JSON Gem - run ```gem install json``` from the command line
  * (Optional) Pretty Print Gem - run ```gem install pp``` from the command line

  Steps to run code:

  * Clone Repo
  * cd into 'examples' directory
  * run ```ruby script.rb <(../sql-to-json [name of sql input file])```

### 5 MORE HOURS?

If I had five more hours to hack at this, I'd complete the validation for ensuring the columns to select are exist within the tables. Then, I'd finish the last part of the prompt which is to either execute the query, or return an error with the failed validation.

With these exercises, it can be tough to optimize on the fly but in general I'd look for opportunities to refactor and make things more efficient. I took steps to ensure all inputs and methods were dynamic in hopes of similating a real world scenario (say for example more tables and sql queries were added to the repo - I'd want my code turnkey enough to run those without having to make changes). All that said here are some specific actions I'd take - I'd look for opportunities to refactor the code by reviewing functions and their outputs, primarily to see if there are better ways to section out behavior. As I gave my work one last 'once-over', I feel that I could have made this cleaner by making use of Regex(s) in a few of these functions. I wasn't happen with my reliance on a few conditionals here and given more time, that'd be one of the first places I'd work to clean things up. I'd also benchmark the run time of each method and seek out better ways to structure them in hopes of making the program faster. Admittedly, it's a simple program that's quick as it is, so that's not of too much concern here but having benchmarks will still help me evaluate how the use of different data structures/enumerables will affect the program. Lastly, I'd write tests for all the methods in the program.

### Anything else?

All in all, I thought this was a fair exercise. The project was scoped to 5 hours but I think I'd benefit from 7 hours to at least get everything working to a satisfactory manner. That said, I feel I was able to make some solid headway in the 5 hours. If you run my program you'll likely see that the output is a boolean, "true" or "false". Since I was unable to finish the final step of executing the query (if valid) and writing it to an output file, I wanted to print the result of the validation checks to give a heads up as to where I was headed.
