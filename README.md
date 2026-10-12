# cs2114-project2-group06
MusicMate playlist app

## How to compile and run
- Open the project in Eclipse
- Run the program: run MusicMateMain
- Run the tests: right-click each Test class and run as a JUnit Test

## Reflection
**What changed from the spec**
- addSong is O(n), not O(1): the provided LinkedChain has no tail pointer, so we use `addToEnd` to keep insertion order.
- addSong rejects a blank title or genre; a blank artist becomes "Unknown Artist".
- Added hashCode() and toString() to Song.
- Titles are trimmed to a maximum of 100 characters.

**What changed in LinkedChain**
Nothing. Our spec listed getSize, getRandom and a tail pointer, but the provided LinkedChain has size() instead, no getRandom, and no tail. The random pick is done in PlaylistManager using get and java.util.Random, so the application logic stays in the app class instead of LinkedChain.

**What happened to our unknowns**
1. Which situations need randomizing: we used shuffleSong and getSongByGenre, which are tested and return null correctly when there is nothing to pick.
2. Random pick from a chain with no index access: the provided LinkedChain has `get(int)` and `size()`, so shuffleSong picks `random.nextInt(playlist.size())` and calls `get`. This is O(n).

**What doesn't work yet**
The MVP path works. We want a better user interface and to keep making adjustments that improve the experience for the user.

## Next Steps
1. Finish polishing the MVP
2. Second element type is done: MusicMateMain stores allowed genres in a LinkedChain<String>
3. Stretch goals: better user history to track their preferences

## Attribution
| File | Who | How |
|---|---|---|
| LinkedChain.java | CS 2114 staff | Provided, used unchanged |
| PlaylistManager.java | Archer, Matthew | Archer wrote; Matthew revised; GenAI-assisted |
| PlaylistManagerTest.java | Archer, Matthew | Archer wrote; Matthew revised; GenAI-assisted |
| Song.java | Ashutosh, Matthew | Ashutosh wrote; Matthew revised; GenAI-assisted |
| SongTest.java | Ashutosh, Matthew | Ashutosh wrote; Matthew revised; GenAI-assisted |
| MusicMateMain.java | Matthew | Wrote; GenAI-assisted |
| Genre.java | Armaan | Wrote | 

**GenAI note:** We used generative AI to help troubleshoot our methods and their tests. When classes didn't compile or communicate well with each other, AI pointed us in the right direction. It also helped with the communication between GitHub and Eclipse since we are all still new to the software.
