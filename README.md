# Connect N Player Template

## Primary Goal
* Publish an implementation of Player to artifactory. Become familiar with software workflows and artifact deployment.

## Secondary Goal
* Produce a champion connect N AI.

## Steps
* Fork this repo into a personal group on gitlab.io
* Change the firstname and surname properties in the pom.xml to your name
* Move the class NameAI from the com.thg.accelerator20.connectn.ai.name package to the com.thg.accelerator20.connectn.ai.firstnamesurname package 
* Rename the NameAI class to whatever you would like to call your AI. Bonus credit for a funny name.
* Implement your Connect N AI logic inside makeMove


## Rules
* For the competition, we will set the width of the board to 10, height to 8, and n to 4. In the case of a draw between two ais, we will change n to 3, so make sure you don't hard-code these parameters in your code (get them from board.getGameConfig())
* Feel free to copy/improve the analysis code [here](https://gitlab.io.thehut.local/accelerator20/connect-n-20/blob/master/src/main/java/com/thehutgroup/accelerator/connectn/analysis/BoardAnalyser.java), although if you do copy it, make sure you change the package so it doesn't conflict with the fully qualified class name in connect n.
* Your solution must use less than 2G of Heap, and terminate within 10 seconds on whichever machine is executing it. Your jar should be no larger than 10MB.
* You may calculate a move database, which can be stored inside your jar. However, no calls to internet services are allowed. 
* If your AI makes a foul move or violates the performance constraints above, it will forfeit the game.

## Making your submission
Once you're ready to submit your entry:
* Change the version in the pom.xml from x-SNAPSHOT to x (e.g. x=1.0)
* Run the command ```mvn deploy```
* Check that the artifact appears in [artifactory](https://artifactory.io.thehut.local/artifactory/webapp/#/artifacts/browse/tree/General/libs-release-local/com/thg/accelerator20/connectn/ai/)
  * If it isn't there, or your deploy command fails, your maven environment isn't set up correctly. Speak to Shaun.
* Push your code to git to keep a record of the source code for version x
* In your pom.xml, increment x and add -SNAPSHOT at the end (e.g. go from 1.0 to 1.1-SNAPSHOT) to get ready for the next version
* Update this [spreadsheet](https://hutgroupnorthwich.sharepoint.com/:x:/s/accelerator20202/EdYIuDN_Il1DvcBXBqZOENsBFGjFeRkonB5YDx-t_sJi7A?e=jtqpOs) with this version