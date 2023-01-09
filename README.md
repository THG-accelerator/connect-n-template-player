# Connect N Player Template

## Primary Goal
* Publish an implementation of Player to a maven repository. Become familiar with software workflows and artifact deployment.

## Secondary Goal
* Produce a champion connect N AI.

## Steps
* Decide if you're working on your own or in a group of up to three.
* Fork this repo into your personal space in github (ensure that it is publicly available)
* Change the name on line 22 of pom.xml (this will set your artifact id)
* Move the class NameAI from the com.thg.accelerator23.connectn.ai.lucky_randomizer package to replace yourteam with the name of your team (lowercase). 
* Rename the NameAI class to whatever you would like to call your AI. Bonus credit for a funny name.
* Implement your Connect N AI logic inside makeMove

## Rules
* For the competition, we will set the width of the board to 10, height to 8, and n to 4.
* Feel free to copy/improve the analysis code [here](https://github.com/THG-accelerator/connect-n-game/blob/master/src/main/java/com/thehutgroup/accelerator/connectn/analysis/BoardAnalyser.java), although if you do copy it, make sure you change the package so it doesn't conflict with the fully qualified class name in connect n.
* Your solution must use less than 2G of Heap, and terminate within 10 seconds on whichever machine is executing it. Your jar should be no larger than 100MB.
* You may calculate a move database, which can be stored inside your jar. However, no calls to internet services are allowed. 
* If your AI makes a foul move or violates the performance constraints above, it will forfeit the game.

## Making your submission
Once you're ready to submit your entry:
* Perform a release in github (we will go through how to do this)
* Enter your repo url into https://jitpack.io/, click "get it", then check the status of the job
* Make a note of the groupId and artifactId -- you will need to specify these in the next step
* Add a json file representing your team into this [repo](https://github.com/THG-accelerator/member_info/tree/main/connect-n)
* Update the version in this file when you are happy with this version

## Timelines
If your solution is published in jitpack and in member_info by Monday 16th Jan 12pm GMT, your solution will be entered in the contest. Later submissions may also be included, but this will be on a best-effort basis.