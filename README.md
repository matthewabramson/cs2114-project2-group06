# cs2114-project2-group06
MusicMate playlist app

# MusicMate

## How to compile and run
- Open the project in Eclipse
- Run the program: run the MusicMateMain
- Run the tests: right-click each Test classes and you can run as a JUnit Test.

## Reflection
**What changed from the spec**
- addSong is O(n), not O(1): the provided LinkedChain has no tail pointer, so we use `addToEnd` to keep insertion order.
- addSong will also reject a null artist or genre and not just a bad title.
- Added PlaylistManager.size() and toString() so things would compile correctly 
- Titles are trimmed to have a maximum 100 characters.

**What changed in LinkedChain**
Our spec listed getSize, getRandom and a tail pointer, but the provided LinkedChain has size() instead, no getRandom, and no tail. The random pick is done in PlaylistManager using get and java.util.Random, so the application logic stays in the app class instead of LinkedChain

**What happened to our unknowns**
1. Which situations need randomizing - We used shuffleSong and getSongByGenre that are tested properly and have correct reactions when null
2. Random pick from a chain with no index access: - The provided LinkedChain has `get(int)` and `size()`, so shuffleSong picks `random.nextInt(playlist.size())` and calls `get`. This is O(n). 

**What doesn't work yet**
We are still trying to figure out what we want past the MVP and how all the classes link together. We want a better user interface 
and to continue making adjustments that will improve the experience for the user.

## Next Steps
1. Finish MVP
2. To add a second element type, like GenreHistory with LinkedChain<String>
3. Stretch goals: better user history to track their preferences. Tracking data and recording it

## Attribution
| LinkedChain.java | CS 2114 staff | Provided, used unchanged |
| PlaylistManager.java | Archer | Wrote; GenAI-assisted |
| PlaylistManagerTest.java | Arhcer | Wrote; GenAI-assisted |
| Song.java |Ashutosh| Wrote; |
| SongTest.java |Ashutosh| Wrote; |
| Genre.java | Armaan | Wrote | 
| MusicMateMain.java | Matthew | Wrote; AI-assisted|

**GenAI note: We used Generative AI to help trouble-shoot our methods and their tests. When classes didn't compile or communicate well with each other, AI pointed us in the right direction. It also helped with the communication between Github and Eclipse since we are all still new with the software
