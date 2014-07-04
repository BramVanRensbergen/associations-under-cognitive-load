Word Associations under Cognitive Load Experiment
=========================

Author: Bram Van Rensbergen (mail@bramvanrensbergen.com) 

Source: https://github.com/BramVanRensbergen/IAT

This is the code to an experiment in which participants give up to three word associations to a number of cues, while simultaneously memorizing a dot pattern.
The experiment was created for personal use (ongoing research), but anyone may use it if they like.

To use:
* Download the project, compile it using Java Development Kit, have the participant run it on a computer with Java Runtime Environment installed.
* You can just change the items in stimuli.txt if you wish to run the experiment with default settings.
* Make sure the 'data' folder exists, and the program has write access to it.
* Many program options can be tweaked in Options.java.
* Instructions/buttons/etc during the experiment are in Dutch, but it is quite easy to translate them: all language displayed to the user is defined in Text.java. Comments, code, output headers, ... are all in English.
* (After any changes to a .java file, you will have to recompile.)


Experiment
--------------

Dot patterns:
* Four dots in a 4x4 grid
* The pattern is either easy (low load) or hard (high load)
* Easy pattern: a row or column of dots
* Hard pattern: a 'complex' arrangement of dots
* A hard pattern fulfills three requirements:
  - No two vertically or horizontally adjacent dots, nor three or four dots in either diagonal. 
  - Complexity has to be high enough according to the CRC method described in: Ichikawa, S (1983). Verbal memory span, visual memory span, and their correlations with cognitive tasks. Japanese Psychological Research 25(4), 173-180.
  - Mirroring or rotating the pattern cannot create a duplicate
* See these papers, or the three classes in pattern.validity, for more information.

Word associations:
* Participants are presented with a cue, described in stimuli.txt
* They give up to three word associations to this cue (the first three words that come to mind)
* They have at most 7 seconds to begin typing a response; if they are too slow, the program moves on to the next cue

Experiment flow:
* Participants see a dot pattern for 750ms
* Participants see a cue, and give up to three associations to it
* Participants see four more cues, one by one, giving up to three associations to each
* Participants are presented with an empty grid and are asked to reproduce the pattern they saw earlier
* The above four steps are repeated as long as there cues left

Stimuli
* Are defined in stimuli.txt
* Difficulty of the stimuli is balanced (not randomized):
  - Stimuli are divided in two lists (second column in this file). Cues in list 1 are all assigned patterns of the same difficulty (easy/hard), cues in list 2 are assigned to the other difficulty.
  - Which list gets assigned which difficulty depends on the participant's ID (even number: list 1 = hard, list 2 = easy; odd: list 1 = easy, list 2 = hard)
  - This means ID has to be numeric!! If you wish to use textual names, you will have to make some changes.
* Stimuli order is randomized:
  1. Stimuli in list 1 and 2 are randomized (within their list)
  2. They are grouped by five (still within list)
  3. Each group of five is assigned a dot pattern of it's assigned difficulty (depending on whether the group contains stimuli from list 1 or 2)
  4. Group order is randomized fully: so it's not like participants first get all items from one difficulty, then the other
* Result: a number of trial groups, each containing five trials from the same list, each with one dot pattern assigned 

Output:
* One entry per association, so three entries per cue
* Each entry holds information on both the association task and the dot pattern task
* Columns for association task: trial number (1..number of cues), group number (1..number of cues divided by 5), index in group (1..5), cue, association, 
    association number (1..3), time to first keypress (for that association), time to submission (for that association), list (1 or 2)
* Columns on the dot pattern task: load (low or high), original pattern (16 digits corresponding to the 4x4 grid, with 0 meaning no dot, and 1 meaning dot), 
    reproduced pattern (again 16 digits), correct (reproduction was exact), hits (nb of dots in reproduction that were also in original), 
    misses (nb of dots in original that were not in reproduction), false alarms (nb of dots in reproduction that were not in original) 
* Note that since participants only reproduced a pattern each five trials, the information on the dot pattern will always be the same in all five trials of a group.






