# Snake and Ladder Game Solver
This project simulates and analyzes an advanced Snake and Ladder game, supporting optimal move calculation, real-time query on snakes/ladders, and strategic snake placement to hinder progress.

## 🚀 Features
- **OptimalMoves()**: Computes the minimum number of dice throws needed to reach the destination (`N`) using Breadth First Search (BFS).
- **Query(x, y)**: Efficiently determines whether adding a snake/ladder between `x` and `y` improves the game's solution.
- **FindBestNewSnake()**: Identifies the most effective snake to add that maximally increases the minimum number of moves needed to finish the game, if possible.

## 🧠 Algorithms Used

- BFS for shortest path computation on the game board.
- Preprocessing to store distances for fast query resolution.
- Optimized ladder overlap detection to find the best snake placement.

## 📁 Code Structure

- `OptimalMoves()`: Returns the optimal number of moves from position 0 to `N`.
- `Query(x, y)`: Returns `+1` if adding a ladder or snake at `(x, y)` improves the optimal path, else `-1`.
- `FindBestNewSnake()`: Returns a pair `[x, y]` representing the best snake to add (where `x > y`) to slow down player progress.

Here's a **concise breakdown** of the logic and structure of your `SnakesLadder` Java code, tailored for **fast revision before interviews**:

---

## 🔧 Constructor: `SnakesLadder(String name)`

Reads input and initializes the board.

### ✅ Step-by-step:

1. **Read board size (`N`)** and number of jumps (`M`).
2. **Initialize arrays**:

   * `snakes[i] = j`: A snake from `i → j`.
   * `ladders[i] = j`: A ladder from `i → j`.
   * `revsnakes[j] = [i1, i2...]`: Reverse of snakes.
   * `revladders[j] = [i1, i2...]`: Reverse of ladders.
   * `stepStart[i]`: Minimum steps from 0 to i.
   * `stepEnd[i]`: Min steps from i to N (used for `Query`).
3. **BFS from start (0):** Calculates `stepStart[]`.

   * Includes jumps (snakes/ladders) in path traversal.
4. **Reverse BFS from N:** Fills `stepEnd[]` for fast reverse lookup.

---

## 🚶‍♂️ `int OptimalMoves()`

Returns the precomputed minimal moves from 0 to N.
→ Just returns `Optim`.

---

## ❓ `int Query(int x, int y)`

Checks if adding jump `x → y` improves the optimal solution.

### Steps:

1. Adjust `y` if it already leads to another cell via a jump.
2. If `stepStart[x] + stepEnd[y] < Optim` → new jump helps → return `+1`.
3. Else → return `-1`.

**Note:** It uses `stepStart[]` and `stepEnd[]` directly to avoid recomputation (very efficient).

---

## 🐍 `int[] FindBestNewSnake()`

Finds the best snake to slow down the player the most.

### Strategy:

1. Only considers **overlapping ladders**:
   * i.e. if `(x1,y1)` and `(x2,y2)` with `x1 < x2 < y1 < y2`, you can add a snake from `y1 → x2`.
2. Sort ladder starts.
3. Precompute minimum `stepEnd` values in prefix-style (`arr[][]`).
4. For every ladder, binary search for an earlier ladder it overlaps with.
5. Pick the snake that gives the **largest increase in steps**.

## 📝 Notes

- Designed for efficiency: avoids O(N²) solutions to prevent time limit exceeded (TLE) errors.
- Built with extendability and modularity in mind.

Based on the input and output you provided, here's an updated, descriptive **`README.md`** file tailored to this Snake and Ladder game implementation:


## 📥 Input Format

- First line: `N` — the destination cell (e.g., 100).
- Next line: Number of ladders and snakes combined (e.g., 11).
- Next lines: Each line denotes a snake or ladder as two integers:
  - `x y` where `x < y` → ladder
  - `x y` where `x > y` → snake
### Example (`input.txt`):
```
100
11
2 37
16 54
9 14
74 87
15 82
50 91
96 76
72 47
29 7
18 6
61 16

````

---

## 🧪 Sample Test Code

```java
SnakesLadder sn = new SnakesLadder("input.txt");
System.out.println("OPTIMAL Move : Expected: 6,  Actual: " + sn.OptimalMoves());
System.out.println("  - Query Result : " + sn.Query(54, 50)); // Should be +1
System.out.println("  - Query Result : " + sn.Query(54, 95)); // Should be +1
System.out.println("  - Query Result : " + sn.Query(54, 10)); // Should be -1

int arr[] = sn.FindBestNewSnake();
System.out.println("BestNewSnake : " + arr[0] + " to " + arr[1]);
````

### 🟢 Sample Output

```
OPTIMAL Move : Expected: 6,  Actual: 6
  - Query Result : 1
  - Query Result : 1
  - Query Result : -1
BestNewSnake : 37 to 15
```
